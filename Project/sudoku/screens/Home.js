import {useTranslation} from "react-i18next";
import React, {useState} from "react";
import i18next from "../services/i18next";
import {Button, FlatList, Modal, Pressable, Text, View} from "react-native";
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
            console.log('action2')
        }else if(action === 'action3') {
            navigation.navigate('Rules');
        }
    }

    function changeTheLanguage(lng) {
        i18next.changeLanguage(lng);
        setVisible(false);
    }

    return (
        <View className="flex-1 items-center justify-center bg-white">
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
            <Text className="text-6xl ">SODUKO</Text>
            <Pressable className="bg-gray-200 p-2 rounded-lg mx-5 mt-20" onPress={() => handleClick('action1')} >
                <Text className="text-3xl text-red-500">{t('menuButtonOneLandingPage')}</Text>
            </Pressable>
            <Pressable className="bg-gray-200 p-2 rounded-lg" onPress={() => handleClick('action2')} >
                <Text className="text-3xl text-red-500">{t('menuButtonTwoLandingPage')}</Text>
            </Pressable>
            <Pressable className="bg-gray-200 p-2 rounded-lg" onPress={() => handleClick('action3')} >
                <Text className="text-3xl text-red-500">{t('menuButtonThreeLandingPage')}</Text>
            </Pressable>
            <Pressable className="bg-gray-200 p-2 rounded-lg" onPress={() => setVisible(true)} >
                <Text className="text-3xl text-red-500">{t('menuButtonFourLandingPage')}</Text>
            </Pressable>
        </View>
    );






}