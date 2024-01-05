import React, { useEffect, useState } from 'react'
import { Text, View, StyleSheet, TouchableHighlight } from 'react-native'
import Ionicons from 'react-native-vector-icons/Ionicons';

function NoteItem({ title, content }) {
    const [type, setType] = useState('');
    const [data, setData] = useState('' || []);

    useEffect(() => {
        console.log('content:', content);
        try {
            const parsedContent = JSON.parse(content);
            setType(parsedContent.type);
            setData(parsedContent.content);
        } catch (error) {
            setData(content); // TODO: remove this line
            console.error('Error parsing content as JSON:', error);
        }
    }, [content])

    const [backgroundColor, setBackgroundColor] = useState('#FFD580');

    return (
        <TouchableHighlight
            onPress={() => { }}
            onPressIn={() => setBackgroundColor('#ffdd9a')}
            onPressOut={() => setBackgroundColor('#ffcd67')}
            underlayColor="transparent"
        >
            <View style={[styles.container, { backgroundColor }]}>
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
    header: { fontSize: 40, fontWeight: 600 },
    title: { fontSize: 25, fontWeight: 600 },
    content: { fontSize: 15, fontWeight: 400, marginTop: '5%' }
});


export default NoteItem
