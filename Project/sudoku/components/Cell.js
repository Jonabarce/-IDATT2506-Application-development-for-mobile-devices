import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';

const Cell = ({ number, cellColor, onSelect }) => {
    return (
        <TouchableOpacity
            style={[styles.cell, { backgroundColor: cellColor || 'transparent' }]} onPress={onSelect}>
            <Text style={styles.cellText}>{number.value === null ? '' : number.value}</Text>
        </TouchableOpacity>
    );
};

const styles = StyleSheet.create({
    cell: {
        width: '33.33%',
        height: '33.33%',
        borderWidth: 1,
        borderColor: '#ddd',
        justifyContent: 'center',
        alignItems: 'center',
    },
    cellText: {
        fontSize: 16,
    },
});

export default Cell;
