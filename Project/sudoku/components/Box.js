import React from 'react';
import {View, StyleSheet, Dimensions} from 'react-native';
import Cell from '../components/Cell';

const Box = () => {
    const numbers = [2, 2, 3, 4, 5, 6, 7, 8, 9];

    return (
        <View style={styles.box}>
            {numbers.map((number, index) => (
                <Cell key={index} number={number} />
            ))}
        </View>
    );
};

const boxBorderWidth = 2;
const cellBorderWidth = 1;
const boxSize = Dimensions.get('window').width / 3 - boxBorderWidth * 2;

const styles = StyleSheet.create({
    box: {
        flexDirection: 'row',
        flexWrap: 'wrap',
        borderWidth: boxBorderWidth,
        borderColor: '#000',
        width: boxSize,
        height: boxSize,
    },
});

export default Box;