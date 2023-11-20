import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';

const CellForDisplay = ({ number }) => {
    return (
        <TouchableOpacity style={[styles.cell]}>
            <Text style={styles.cellText}>{number === null || number === 0 ? '' : number}</Text>
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

export default CellForDisplay;
