import {useTranslation} from "react-i18next";
import React, {useState} from "react";
import i18next from "../services/i18next";
import {Button, FlatList, Modal, Pressable, StyleSheet, Text, View} from "react-native";






export default function Rules() {

    const { t } = useTranslation();

    return (
        <View style={styles.board}>
            <Text style={styles.title} className="">{ "1." + t('content')}</Text>
            <Text className=""></Text>
            <Text className=""></Text>
            <Text style={styles.title} className="">{ "2." + t('rule1')}</Text>
            <Text className=""></Text>
            <Text className=""></Text>
            <Text style={styles.title} className="">{ "3." + t('rule2')}</Text>
            <Text className=""></Text>
            <Text className=""></Text>
            <Text style={styles.title} className="">{ "4." + t('rule3')}</Text>
            <Text className=""></Text>
            <Text className=""></Text>
            <Text style={styles.title} className="">{"5." + t('rule4')}</Text>
        </View>
    )
}



const styles = StyleSheet.create({
    board: {
        display: 'flex',
        justifyContent: 'top',
        alignItems: 'left',
        backgroundColor: '#121212',
        height: '100%',
        width: '100%',
    },

    title: {
        fontWeight: 'bold',
        color: '#FFFFFF',
        fontSize: 15,
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
