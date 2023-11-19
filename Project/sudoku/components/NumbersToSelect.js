import {TouchableOpacity, View, StyleSheet, Text} from "react-native";


export default function NumbersToSelect({onSelectNumber})  {
    let numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, "Delete", "Red"];
    return (
        <View style={styles.numbersToSelect}>
            {numbers.map((number) => (
                <TouchableOpacity
                    key={number}
                    style={styles.numberButton}
                    onPress={() => onSelectNumber(number)}
                >
                    <Text style={styles.numberText}>{number}</Text>
                </TouchableOpacity>
            ))}
        </View>
    );
}



const styles = StyleSheet.create({
    numbersToSelect: {
        flexDirection: 'row',
        flexWrap: 'wrap',
        justifyContent: 'space-between',
        marginTop: 20,
    },
    numberButton: {
        width: '30%',
        height: 40,
        backgroundColor: '#ddd',
        justifyContent: 'center',
        alignItems: 'center',
        marginBottom: 10,
    },
    numberText: {
        fontSize: 20,
    },
});
