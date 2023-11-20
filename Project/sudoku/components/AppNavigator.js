import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import BoardScreen from '../screens/Board';
import HomeScreen from '../screens/Home';
import RulesScreen from '../screens/Rules';
import GenerateBoardScreen from "../screens/GenerateBoard";
import {useTranslation} from "react-i18next";



const Stack = createNativeStackNavigator();

export default function AppNavigator() {

    const { t } = useTranslation();

    return (
        <NavigationContainer >
            <Stack.Navigator initialRouteName="Home">
                <Stack.Screen
                    name="Home"
                    component={HomeScreen}
                    options={{
                        title: '',
                        headerStyle: {
                            backgroundColor: '#121212',
                        },
                        headerTintColor: '#FFFFFF',
                        headerTitleStyle: {
                            fontWeight: 'bold',
                        },
                    }}
                />
                <Stack.Screen
                    name="Board"
                    component={BoardScreen}
                    options={{
                        title: t('board'),
                        headerStyle: {
                            backgroundColor: '#121212',
                        },
                        headerTintColor: '#FFFFFF',
                        headerTitleStyle: {
                            fontWeight: 'bold',
                        },
                    }}
                />
                <Stack.Screen
                    name="GenerateBoard"
                    component={GenerateBoardScreen}
                    options={{
                        title: t('generate'),
                        headerStyle: {
                            backgroundColor: '#121212',
                        },
                        headerTintColor: '#FFFFFF',
                        headerTitleStyle: {
                            fontWeight: 'bold',
                        },
                    }}
                />
                <Stack.Screen
                    name="Rules"
                    component={RulesScreen}
                    options={{
                        title: t('rules'),
                        headerStyle: {
                            backgroundColor: '#121212',
                        },
                        headerTintColor: '#FFFFFF',
                        headerTitleStyle: {
                            fontWeight: 'bold',
                        },
                    }}
                />
            </Stack.Navigator>
        </NavigationContainer>
    );
}

