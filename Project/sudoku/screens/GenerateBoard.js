import React, { useState, useEffect } from 'react';
import {View, StyleSheet, Button, Alert, Pressable, Text} from 'react-native';
import Toast from 'react-native-simple-toast';
import AsyncStorage from '@react-native-async-storage/async-storage';
import BoxForDisplay from '../components/BoxForDisplay';
import {useTranslation} from "react-i18next";
import easyBoard from '../assets/data/easy.json';
import normalBoard from '../assets/data/normal.json';
import hardBoard from '../assets/data/hard.json';
import { useNavigation } from '@react-navigation/native';



export default function GenerateBoard() {
    const navigation = useNavigation();
    const [selectedDifficulty, setSelectedDifficulty] = useState('');
    const [boardData, setBoardData] = useState([]);
    const [solution, setSolution] = useState([]);
    const [selectedCell, setSelectedCell] = useState(null);
    const { t } = useTranslation();

    useEffect(() => {
        saveBoardFirstTime();
    }, []);

    const saveBoardFirstTime = async () => {
        try {
            const currentBoards = await AsyncStorage.getItem('currentBoardsToPlayWith');
            if (!currentBoards) {
                const boardsToSave = [easyBoard, normalBoard, hardBoard];
                await AsyncStorage.setItem('currentBoardsToPlayWith', JSON.stringify(boardsToSave));
            }
        } catch (error) {
            showToast(t('saveBoardError'));
        }
    };

    function showToast (text) {
        Toast.show(text, Toast.SHORT);
    }

    function reformatGridData(apiGridData) {
        const theSudokuGroups = [];

        for (let i = 0; i < 9; i += 3) {
            for (let j = 0; j < 9; j += 3) {
                const group = [];

                for (let k = i; k < i + 3; k++) {
                    for (let l = j; l < j + 3; l++) {
                        group.push(apiGridData[k][l]);
                    }
                }

                theSudokuGroups.push(group);
            }
        }

        return theSudokuGroups;
    }

    const getNewRandomBoardFromAPI = async () => {
        try {
            const response = await fetch('https://sudoku-api.vercel.app/api/dosuku', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            if (!response.ok) {
                throw new Error(`HTTP Error: ${response.statusText}`);
            }

            const responseText = await response.text();
            try {
                const responseData = JSON.parse(responseText);
                console.log("Response Data:", responseData);

                const apiGridData = responseData.newboard.grids[0].value;
                const apiSolutionData = responseData.newboard.grids[0].solution;

                console.log("API Grid Data:", apiGridData);
                const reformattedGridData = reformatGridData(apiGridData);

                console.log("Board reformatted:", reformattedGridData);
                setBoardData(reformattedGridData);

                console.log("API Solution Data:", responseData.newboard.grids[0].solution);
                const reformattedSolutionData = reformatGridData(apiSolutionData);
                console.log("Solution reformatted:", reformattedSolutionData);
                setSolution(reformattedSolutionData);
                console.log("Difficulty:", responseData.newboard.grids[0].difficulty);
                const apiDifficulty = responseData.newboard.grids[0].difficulty.toLowerCase();
                setSelectedDifficulty(apiDifficulty);
            } catch (jsonError) {
                console.error("JSON Parsing Error:", jsonError);
                throw new Error('Failed to parse JSON');
            }
        } catch (error) {
            console.error("Network or other error:", error);
            showToast(t('failedToFetchNewBoard'));
        }
    };

    const saveRandomBoardFromApi = async () => {
        const boardToSave = {
            board: boardData.map(row => row.map(cell => cell === 0 ? null : cell)),
            solution: solution,
            difficulty: selectedDifficulty.toLowerCase(),
        };
        const currentBoards = await AsyncStorage.getItem('currentBoardsToPlayWith');
        const boards = currentBoards ? JSON.parse(currentBoards) : [];

        console.log("BoardToSave:", boardToSave);
        boards.push(boardToSave);
        await AsyncStorage.setItem('currentBoardsToPlayWith', JSON.stringify(boards));
        showToast(t('savedBoard'));
        navigation.navigate('Home');
        navigation.navigate('GenerateBoard');
    };



    return (
        <View style={styles.boardHolder}>
            {boardData.length > 0 ? (
                <>
                    <View style={styles.board}>
                        {boardData.map((row, rowIndex) => (
                            <BoxForDisplay
                                key={rowIndex}
                                rowData={row}
                                rowIndex={rowIndex}
                            />
                        ))}
                    </View>
                    <View>
                        <View>
                            <Text style={styles. colorForTextButtonDifficulty}>
                                {selectedDifficulty === 'easy' ? t('theEasyDiffiulty') :
                                    selectedDifficulty === 'medium' ? t('theNormalDifficulty') :
                                        selectedDifficulty === 'hard' ? t('theHardDifficulty') :
                                            ''}
                            </Text>
                        </View>
                        <Pressable className="bg-blue-400 p-2 rounded-lg" onPress={saveRandomBoardFromApi} >
                            <Text style={styles.colorForTextButton} >{t('saveNewBoard')}</Text>
                        </Pressable>
                        <Pressable className="bg-purple-400 p-2 rounded-lg" onPress={() => setBoardData([])} >
                            <Text style={styles.colorForTextButton} >{t('generateNewBoard')}</Text>
                        </Pressable>
                    </View>
                </>
            ) : (
                <View className="w-full flex justify-center place-items-center items-center">
                    <Pressable className="bg-blue-400 p-2 rounded-lg" onPress={getNewRandomBoardFromAPI} >
                        <Text>{t('generateNewBoard')}</Text>
                    </Pressable>
                </View>
            )}
        </View>
    );
}




const styles = StyleSheet.create({
    board: {
        flexDirection: 'row',
        flexWrap: 'wrap',
        justifyContent: 'center',
        alignItems: 'center',
        padding: 2,
        backgroundColor: '#121212'
    },

    boardHolder:{
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#121212'
    },
    title: {
        fontWeight: 'bold',
        color: '#FFFFFF',
        fontSize: 30,
    },
    background: {
        backgroundColor: '#121212'
    },
    backgroundForButton: {
        backgroundColor: '#FFFFFF',
    },
    colorForText: {
        color: '#000000',
    },

    colorForTextButton: {
        color: '#FFFFFF',
    },
    colorForTextButtonDifficulty: {
        color: '#FFFFFF',
        fontSize: 20,
    }

});
