import {useTranslation} from "react-i18next";
import React, {useState} from "react";
import i18next from "../services/i18next";
import {Button, FlatList, Modal, Pressable, Text, View} from "react-native";






export default function Rules() {

    const { t } = useTranslation();

    return (
        <View>
            <Text className="">{t('content')}</Text>
            <Text className="">{t('rule1')}</Text>
            <Text className="">{t('rule2')}</Text>
            <Text className="">{t('rule3')}</Text>
            <Text className="">{t('rule4')}</Text>
        </View>
    )
}