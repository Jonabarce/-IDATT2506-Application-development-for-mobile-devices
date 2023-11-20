import React, { useState, useEffect } from 'react';
import {View, StyleSheet, ToastAndroid, Button, Alert, Pressable, Text} from 'react-native';
import i18next from "../services/i18next";
import AsyncStorage from '@react-native-async-storage/async-storage';
import Box from '../components/Box';
import NumbersToSelect from '../components/NumbersToSelect';
import {useTranslation} from "react-i18next";
import easyBoard from '../assets/data/easy.json';
import normalBoard from '../assets/data/normal.json';
import hardBoard from '../assets/data/hard.json';


export default function Board() {
    const [selectedDifficulty, setSelectedDifficulty] = useState(null);
    const [boardData, setBoardData] = useState([]);
    const [solution, setSolution] = useState([]);
    const [selectedCell, setSelectedCell] = useState(null);
    const { t } = useTranslation();

    function showToast (text) {
        ToastAndroid.show(text, ToastAndroid.SHORT);
    }

    useEffect(() => {
        checkForSavedBoard();
        saveBoardFirstTime();
    }, []);

    const setTheBoardData = (savedBoard, savedSolution) => {
        setBoardData(savedBoard);
        setSolution(savedSolution);
    }

    const checkForSavedBoard = async () => {
        try {
            const savedBoardString = await AsyncStorage.getItem('currentBoard');
            const savedSolutionString = await AsyncStorage.getItem('currentBoardSolution');
            const savedBoard = savedBoardString ? JSON.parse(savedBoardString) : null;
            const savedSolution = savedSolutionString ? JSON.parse(savedSolutionString) : null;
            console.log("Saved solution:", savedSolution);
            console.log("Saved board:", savedBoard);

            if (savedBoard && savedSolution) {
                Alert.alert(
                    t('title'),
                    t('message'),
                    [
                        {
                            text: t('textNo'),
                            style: 'cancel',
                        },
                        {
                            text: t('textYes'),
                            onPress: () => {
                                setTheBoardData(savedBoard, savedSolution);
                            },
                        },
                    ],
                    { cancelable: false }
                );
            }
        } catch (error) {
            showToast(t('failedToLoadBoard'));
        }
    };

    const updateCell = (newValue) => {
        if (selectedCell) {
            const [rowIndex, cellIndex] = selectedCell;
            let selectedCellData = boardData[rowIndex][cellIndex];

            if (selectedCellData.isPredefined) {
                showToast(t('cantBeChanged'));
            } else {
                let newBoardData = [...boardData];
                if (newValue === 'Delete') {
                    newBoardData[rowIndex][cellIndex].value = "";
                    newBoardData[rowIndex][cellIndex].isUncertain = false;
                } else if (newValue === 'Red') {
                    newBoardData[rowIndex][cellIndex].isUncertain = true;
                }
                else {
                    newBoardData[rowIndex][cellIndex].value = newValue;
                    newBoardData[rowIndex][cellIndex].color = 'transparent';
                    newBoardData[rowIndex][cellIndex].isUncertain = false;
                }
                setBoardData(newBoardData);
                setSelectedCell(null);

                if (checkIfBoardIsSolved()) {
                    showToast(t('solvedBoard'));
                }
                saveBoardToStorage(newBoardData, solution);
            }
        }
    };

    const loadBoard = async (difficulty) => {
        try {
            const currentBoards = await AsyncStorage.getItem('currentBoardsToPlayWith');
            let boards = currentBoards ? JSON.parse(currentBoards) : [];
            console.log("Boards:", boards.length);
            let filteredBoards = boards.filter(board => board.difficulty === difficulty);
            console.log("Filtered boards:", filteredBoards);
            if (filteredBoards.length > 0) {
                let randomIndex = Math.floor(Math.random() * filteredBoards.length);
                let boardDetails = filteredBoards[randomIndex];
                const convertedBoard = boardDetails.board.map(row =>
                    row.map(cell => ({
                        value: cell,
                        isPredefined: cell !== null
                    }))
                );
                setBoardData(convertedBoard);
                console.log("Solution:", boardDetails.solution);
                setSolution(boardDetails.solution);
                setSelectedDifficulty(difficulty);
            } else {
                showToast(t('noBoardFoundForDifficulty'));
            }
        } catch (error) {
            showToast(t('failedToLoadBoard'));
        }
    };

    const saveBoardToStorage = async (boardToSave, solutionToSave) => {
        try {
            console.log("Saving board:", boardToSave);
            console.log("Saving solution:", solutionToSave);
            await AsyncStorage.setItem('currentBoard', JSON.stringify(boardToSave));
            if (solutionToSave) {
                await AsyncStorage.setItem('currentBoardSolution', JSON.stringify(solutionToSave));
            } else {
                console.log("Solution is undefined, not saving.");
            }
        } catch (error) {
            console.log("Error saving to storage:", error);
            showToast(t('saveBoardError'));
        }
    };



    const saveBoardFirstTime = async () => {
        try {
            const currentBoards = await AsyncStorage.getItem('currentBoardsToPlayWith');
            if (!currentBoards) {
                const boardsToSave = [easyBoard, normalBoard, hardBoard];
                await AsyncStorage.setItem('currentBoardsToPlayWith', JSON.stringify(boardsToSave));
                showToast(t('boardsSaved'));
            }
        } catch (error) {
            showToast(t('saveBoardError'));
        }
    };

    const checkIfBoardIsSolved = () => {
        for (let i = 0; i < boardData.length; i++) {
            for (let j = 0; j < boardData[i].length; j++) {
                if (boardData[i][j].value !== solution[i][j]) {
                    return false;
                }
            }
        }
        return true;
    };


    return (
        <View style={styles.boardHolder}>
            {boardData.length > 0 ? (
                <View style={styles.board}>
                    {boardData.map((row, rowIndex) => (
                        <Box
                            key={rowIndex}
                            rowData={row}
                            rowIndex={rowIndex}
                            onSelectCell={setSelectedCell}
                        />
                    ))}
                    <NumbersToSelect onSelectNumber={updateCell} />
                </View>
            ) : (

                <View className="w-full flex justify-center place-items-center items-center">
                    <View>
                        <Text style={styles.title} className="text-6xl ">{t('selectDifficulty')}</Text>
                    </View>
                    <View>
                        <Pressable className="bg-green-400 p-2 rounded-lg "  onPress={() => loadBoard('easy')} >
                            <Text >{t('easyBoard')}</Text>
                        </Pressable>
                        <Pressable className="bg-yellow-300 p-2 rounded-lg" onPress={() => loadBoard('normal')} >
                            <Text >{t('normalBoard')}</Text>
                        </Pressable>
                        <Pressable className="bg-red-500 p-2 rounded-lg" onPress={() => loadBoard('hard')} >
                            <Text >{t('hardBoard')}</Text>
                        </Pressable>
                    </View>
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
    }

});
