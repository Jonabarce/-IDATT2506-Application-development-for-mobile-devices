import React, {useEffect} from 'react';
import { View, StyleSheet } from 'react-native';
import Box from '../components/Box';


const getSudokuPuzzle = async (difficulty) => {
    try {
        let response = await fetch(``);
        console.log('response', response);
    } catch (error) {
        console.error('Error fetching Sudoku puzzle:', error);
    }
};



export default function Board() {

    useEffect(() => {
        getSudokuPuzzle('easy');
    }, []);


    return (
        <View style={styles.boardHolder}>
        <View style={styles.board}>
            {[...Array(9)].map((_, index) => (
                <Box key={index} style={styles.box} />
            ))}
        </View>
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
