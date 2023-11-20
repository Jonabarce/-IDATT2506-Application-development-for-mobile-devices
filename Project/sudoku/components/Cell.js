import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';

const Cell = ({ number, cellColor, onSelect, isPredefined, isUncertain }) => {
    const backgroundColor = isUncertain ? 'red' : isPredefined ? 'lightgray' : cellColor || 'transparent';

    return (
        <TouchableOpacity
            style={[styles.cell, { backgroundColor }]}
            onPress={onSelect}
        >
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
