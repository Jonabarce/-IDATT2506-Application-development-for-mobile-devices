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



export default function GenerateBoard() {
const [selectedDifficulty, setSelectedDifficulty] = useState(null);
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
                showToast(t('boardsSaved'));
            }
        } catch (error) {
            showToast(t('saveBoardError'));
        }
    };

    function showToast (text) {
        ToastAndroid.show(text, ToastAndroid.SHORT);
    }

    const getNewRandomBoardFromAPI = async (difficulty) => {
        try {
            const query = JSON.stringify({
                query: `{
              newboard(limit: 1, difficulty: "${difficulty}") {
                grids {
                  value
                  solution
                  difficulty
                }
              }
            }`
            });

            const response = await fetch('https://sudoku-api.vercel.app/api/dosuku', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: query,
            });

            if (!response.ok) {
                throw new Error(`HTTP Error: ${response.statusText}`);
            }

            const responseText = await response.text();
            try {
                const responseData = JSON.parse(responseText);
                console.log("Response Data:", responseData);

                const grid = responseData.newboard.grids[0];
                console.log("Board:", grid.value);
                setBoardData(grid.value);
                console.log("Solution:", grid.solution);
                setSolution(grid.solution);
                setSelectedDifficulty(difficulty);
            } catch (jsonError) {
                console.error("JSON Parsing Error:", jsonError);
                throw new Error('Failed to parse JSON');
            }
        } catch (error) {
            console.error("Network or other error:", error);
            showToast('Failed to fetch new board. Please try again.');
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
    };

    const deleteRandomBoardsFromApiFromStorage = async () => {

    }

    const listRandomBoardsFromApiFromStorage = async () => {

    }



    return (
        <View style={styles.boardHolder}>
            {boardData.length > 0 ? (
                <>
                    <View style={styles.board}>
                        {boardData.map((row, rowIndex) => (
                            <Box
                                key={rowIndex}
                                rowData={row}
                                rowIndex={rowIndex}
                            />
                        ))}
                    </View>
                    <View>
                        <Pressable className="bg-blue-400 p-2 rounded-lg" onPress={saveRandomBoardFromApi} >
                            <Text >{t('saveBoard')}</Text>
                        </Pressable>
                        <Pressable className="bg-purple-400 p-2 rounded-lg" onPress={() => setBoardData([])} >
                            <Text >{t('generateNew')}</Text>
                        </Pressable>
                    </View>
                </>
            ) : (
                <View>
                    <Text>{t('selectDifficulty')}</Text>
                    <Pressable className="bg-green-400 p-2 rounded-lg" onPress={() => getNewRandomBoardFromAPI('easy')} >
                        <Text >{t('easyBoard')}</Text>
                    </Pressable>
                    <Pressable className="bg-yellow-300 p-2 rounded-lg" onPress={() => getNewRandomBoardFromAPI('normal')} >
                        <Text >{t('normalBoard')}</Text>
                    </Pressable>
                    <Pressable className="bg-red-500 p-2 rounded-lg" onPress={() => getNewRandomBoardFromAPI('hard')} >
                        <Text >{t('hardBoard')}</Text>
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
        backgroundColor: '#fff',
    },

    boardHolder:{
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#fff',
    },

});