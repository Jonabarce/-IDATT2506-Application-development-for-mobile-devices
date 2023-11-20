import { View, StyleSheet, Dimensions } from 'react-native';
import CellForDisplay from '../components/CellForDisplay';

const BoxForDisplay = ({ rowData, rowIndex }) => {
    return (
        <View style={styles.box}>
            {rowData.map((number, cellIndex) => (
                <CellForDisplay
                    key={cellIndex}
                    number={number}
                />
            ))}
        </View>
    );
};




const boxBorderWidth = 2;
const boxSize = Dimensions.get('window').width / 3 - boxBorderWidth * 2;

const styles = StyleSheet.create({
    box: {
        flexDirection: 'row',
        flexWrap: 'wrap',
        borderWidth: boxBorderWidth,
        borderColor: '#000',
        width: boxSize,
        height: boxSize,
        backgroundColor: '#FFFFFF'
    },
});

export default BoxForDisplay;