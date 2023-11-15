import React, { useState, useEffect } from 'react';
import { View, StyleSheet, TouchableOpacity, Text, Button } from 'react-native';
import Box from '../components/Box';
import easyBoard from '../assets/data/easy.json';
import normalBoard from '../assets/data/normal.json';
import hardBoard from '../assets/data/hard.json';



export default function Board() {
    const [selectedDifficulty, setSelectedDifficulty] = useState(null);
    const [boardData, setBoardData] = useState([]);
    const [selectedCell, setSelectedCell] = useState(null);


    useEffect(() => {
        if (selectedDifficulty) {
            loadBoard(selectedDifficulty);
        }
    }, [selectedDifficulty]);


    const loadBoard = (difficulty) => {
        switch(difficulty) {
            case 'easy':
                setBoardData(easyBoard);
                break;
            case 'normal':
                setBoardData(normalBoard);
                break;
            case 'hard':
                setBoardData(hardBoard);
                break;
            default:
                setBoardData(null);
        }
    };

    return (
        <View style={styles.boardHolder}>
            {boardData.length > 0 ? (
                <View style={styles.board}>
                    {boardData.map((row, rowIndex) => (
                        <Box key={rowIndex} rowData={row} />
                    ))}
                </View>
            ) : (
                <View>
                    <Button title="Easy" onPress={() => setSelectedDifficulty('easy')} />
                    <Button title="Normal" onPress={() => setSelectedDifficulty('normal')} />
                    <Button title="Hard" onPress={() => setSelectedDifficulty('hard')} />
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
