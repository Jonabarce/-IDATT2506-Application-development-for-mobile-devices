import {TouchableOpacity, View, StyleSheet, Text} from "react-native";
import {useTranslation} from "react-i18next";



export default function NumbersToSelect({onSelectNumber})  {
    const { t } = useTranslation();


    let numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, t('delete'), t('notSure')];
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
        backgroundColor: '#121212'
    },
    numberButton: {
        width: '30%',
        height: 40,
        backgroundColor: '#BB86FC',
        justifyContent: 'center',
        alignItems: 'center',
        marginBottom: 10,
        borderRadius: 10,
        color: 'white',
    },
    numberText: {
        fontSize: 20,
        color: 'white'
    },
});
