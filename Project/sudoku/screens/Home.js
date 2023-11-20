import {useTranslation} from "react-i18next";
import React, {useState} from "react";
import i18next from "../services/i18next";
import {Button, FlatList, Modal, Pressable, StyleSheet, Text, View} from "react-native";
import languagesList from "../services/languagesList.json";
import { useNavigation } from '@react-navigation/native';

export default function Home() {
    const navigation = useNavigation();
    const { t } = useTranslation();
    const [visible, setVisible] = useState(false);

    function handleClick(action) {
        if(action === 'action1'){
            navigation.navigate('Board');
        }else if(action === 'action2'){
            navigation.navigate('GenerateBoard');
        }else if(action === 'action3') {
            navigation.navigate('Rules');
        }
    }

    function changeTheLanguage(lng) {
        i18next.changeLanguage(lng);
        setVisible(false);
    }

    return (
        <View style={styles.background} className="flex-1 items-center justify-center">
            <Modal className="flex-1 items-center justify-center "
                   visible={visible}
                   onRequestClose={() => setVisible(false)}
                   animationType="slide"
                   transparent={false}
            >
                <View className="flex-1 items-center justify-center p-4 mx-4 mt-20 bg-white">
                    <FlatList
                        data={Object.keys(languagesList)}
                        renderItem={({ item }) => (
                            <Button
                                onPress={() => changeTheLanguage(item)}
                                title={languagesList[item].nativeName}
                            />
                        )}
                        keyExtractor={(item) => item}
                    />
                </View>
            </Modal>
            <Text style={styles.title} className="text-6xl ">SUDOKU</Text>
            <Pressable style={styles.backgroundForButton} className="bg-gray-200 w-full p-2 rounded-lg flex items-center" onPress={() => handleClick('action1')} >
                <Text style={styles.colorForText} className="text-3xl text-red-500">{t('menuButtonOneLandingPage')}</Text>
            </Pressable>
            <Pressable style={styles.backgroundForButton} className="bg-gray-200 w-full p-2 rounded-lg flex items-center" onPress={() => handleClick('action2')} >
                <Text style={styles.colorForText} className="text-3xl text-red-500">{t('menuButtonTwoLandingPage')}</Text>
            </Pressable>
            <Pressable style={styles.backgroundForButton} className="bg-gray-200 w-full p-2 rounded-lg flex items-center" onPress={() => handleClick('action3')} >
                <Text style={styles.colorForText} className="text-3xl text-red-500">{t('menuButtonThreeLandingPage')}</Text>
            </Pressable>
            <Pressable style={styles.backgroundForButton} className="bg-gray-200 w-full p-2 rounded-lg flex items-center" onPress={() => setVisible(true)} >
                <Text style={styles.colorForText} className="text-3xl text-red-500">{t('menuButtonFourLandingPage')}</Text>
            </Pressable>
        </View>
    );






}


const styles = StyleSheet.create({
    title: {
        fontWeight: 'bold',
        color: '#BB86FC',
    },
    background: {
        backgroundColor: '#121212'
    },
    backgroundForButton: {
        backgroundColor: '#FFFFFF',
    },
    colorForText: {
        color: '#000000',
    }
});