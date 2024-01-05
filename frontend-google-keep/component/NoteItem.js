import React, { useEffect, useState } from 'react'
import { Text, View, StyleSheet, TouchableHighlight, CheckBox } from 'react-native'
import Ionicons from 'react-native-vector-icons/Ionicons';
import { useNavigation } from '@react-navigation/native';

function NoteItem({ id, title, content }) {
    const [type, setType] = useState('');
    const [data, setData] = useState('' || []);

    const navigation = useNavigation();

    useEffect(() => {
        console.log('content:', content);
        try {
            const parsedContent = JSON.parse(content);
            setType(parsedContent.type);
            setData(parsedContent.content);
        } catch (error) {
            console.error('Error parsing content as JSON:', error);
        }
    }, [content])

    const [backgroundColor, setBackgroundColor] = useState('#FFD580');

    return (
        <TouchableHighlight
            onPress={() => { }}
            onPressIn={() => {
                setBackgroundColor('#ffdd9a');
                navigation.navigate('Edit', {
                    title: title,
                    content: content,
                    id: id,
                });
            }}
            onPressOut={() => setBackgroundColor('#ffcd67')}
            underlayColor="transparent">
            <View style={[styles.container, { backgroundColor }, styles.shadowProp]}>
                <View style={{ flexDirection: 'row', justifyContent: 'space-between' }}>
                    <Text style={styles.title}> {title} </Text>
                    <Ionicons name="pencil" size={15} color="black" />
                </View>
                <Text style={styles.content}>
                    {/* {type === 'text' ? data : null} */}
                    {type !== 'numbered' ? data : null}
                    {type === 'numbered'
                        ? data.map((item, index) => (
                            <Text key={index}> {index + 1}. {item}{'\n'}</Text>
                        ))
                        : null}
                </Text>
            </View>
        </TouchableHighlight>
    )
}

const styles = StyleSheet.create({
    container: {
        justifyContent: 'flex-start',
        marginTop: '2%',
        backgroundColor: '#FFD580',
        padding: '2%',
        borderRadius: 10,
    },
    shadowProp: {
        shadowColor: '#171717',
        shadowOffset: { width: -2, height: 4 },
        shadowOpacity: 0.2,
        shadowRadius: 3,
    },
    header: { fontSize: 40, fontWeight: 600 },
    title: { fontSize: 25, fontWeight: 600 },
    content: { fontSize: 15, fontWeight: 400, marginTop: '5%' }
});


export default NoteItem
