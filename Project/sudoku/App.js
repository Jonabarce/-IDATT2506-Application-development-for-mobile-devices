import { StatusBar } from 'expo-status-bar';
import React, { useState } from 'react';
import { Button, View, Text, Modal, FlatList } from 'react-native';
import i18next from './services/i18next';
import { useTranslation } from 'react-i18next';
import languagesList from './services/languagesList';

export default function App() {
  const { t } = useTranslation();
  const [visible, setVisible] = useState(false);

  function handleClick(action) {
    console.log(action);
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
      <Text>SODUKO</Text>
      <StatusBar style="auto" />
      <Button onPress={() => handleClick('action1')} title={t('menuButtonOneLandingPage')} />
      <Button onPress={() => handleClick('action2')} title={t('menuButtonTwoLandingPage')} />
      <Button onPress={() => handleClick('action3')} title={t('menuButtonThreeLandingPage')} />
      <Button onPress={() => setVisible(true)} title={t('menuButtonFourLandingPage')} />
    </View>
  );
}