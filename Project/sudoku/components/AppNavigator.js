import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import BoardScreen from '../screens/Board';
import HomeScreen from '../screens/Home';

const Stack = createNativeStackNavigator();

export default function AppNavigator() {
    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName="Home">
                <Stack.Screen name="Home" component={HomeScreen} />
                <Stack.Screen name="Board" component={BoardScreen} />
            </Stack.Navigator>
        </NavigationContainer>
    );
}