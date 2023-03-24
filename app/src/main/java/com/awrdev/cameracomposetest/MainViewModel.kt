package com.awrdev.cameracomposetest

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.awrdev.cameracomposetest.common.*

class MainViewModel: ViewModel() {

    private val _state = mutableStateOf(MainState())
    val state: State<MainState> = _state

    var isLogging = false
    val morseUnit = MorseUnit()




//    fun receiveSignal(signal: String){
//        _state.value = state.value.copy(currentSignal = signal)
//        if (signal == state.value.lastSignal){
//            return
//        }
//        else {
//            updateLastSignal(signal)
//        }
//    }

//    private fun updateLastSignal(signal: String) {
//        val currentTime = System.currentTimeMillis()
//        val lastSignalTime = currentTime - state.value.lastSignalTime
//        var message = state.value.message.toMutableList()
//        println(state.value.lastSignalTime)
//        if (lastSignalTime > DASH_LENGTH.last+ERROR_RATE){
//            if (message.isEmpty()){
//                //message.add(signal)
//            }
//            else {
//                if (signal == "HIGH" && lastSignalTime in SPACE_LENGTH) message.add("PAUSE")
//                else {
//                    println("Not a valid Morse symbol!!!! $signal $lastSignalTime")
//
//                    decodeArray()
//                    message = mutableListOf()
//
//                    //message.removeLast()
//                    //message.add(signal)
//                }
//            }
//        }
//        else{
//            //message.add(signal)
//            println(lastSignalTime)
//            if (signal == "LOW" && lastSignalTime in DOT_LENGTH) message.add("DOT")
//            if (signal == "LOW" && lastSignalTime in DASH_LENGTH) message.add("DASH")
//            if (signal == "HIGH" && lastSignalTime in LETTER_BRAKE_LENGTH){
//                decodeArray()
//                message = mutableListOf()
//            }
//
//                //else return
//            //Think about LOWsgnals
//        }
//        val logs: String
//        if (isLogging){
//            logs = "${state.value.log_text}\n$signal $lastSignalTime"
//        }
//        else {
//            logs = state.value.log_text
//        }
//        _state.value = state.value.copy(
//            lastSignal = signal,
//            lastSignalTime = currentTime,
//            message = message,
//            log_text = logs
//        )
//    }

//    private fun decodeArray() {
//        val alphabet = mapOf<String, Char>(
//            ".-" to 'A',
//            "-..." to 'B',
//            "-.-." to 'C',
//            "-.." to 'D',
//            "." to 'E',
//            "..-." to 'F',
//            "--." to 'G',
//            "...." to 'H',
//            ".." to 'I',
//            ".---" to 'J',
//            "-.-" to 'K',
//            ".-.." to 'L',
//            "--" to 'M',
//            "-." to 'N',
//            "---" to 'O',
//            ".--." to 'P',
//            "--.-" to 'Q',
//            ".-." to 'R',
//            "..." to 'S',
//            "-" to 'T',
//            "..-" to 'U',
//            "...-" to 'V',
//            ".--" to 'W',
//            "-..-" to 'X',
//            "-.--" to 'Y',
//            "--.." to 'Z',
//            ".----" to '1',
//            "..---" to '2',
//            "...--" to '3',
//            "....-" to '4',
//            "....." to '5',
//            "-...." to '6',
//            "--..." to '7',
//            "---.." to '8',
//            "----." to '9',
//            "-----" to '0'
//        )
//        println("message to decode ${state.value.message.joinToString()}")
//        val chars: MutableList<Char> = mutableListOf()
//        for (word in state.value.message){
//            if (word == "DOT") chars.add('.')
//            if (word == "DASH") chars.add('-')
//        }
//        val letterMorse = chars.joinToString(separator = "")
//        val words = state.value.words.toMutableList()
//        words.add(alphabet[letterMorse].toString())
//        _state.value = state.value.copy(words = words)
//        //TODO("Decoding symbols row into alphabet")
//    }

    fun updateLuminosuty(luma: Double) {
        if (luma > state.value.maxLumen){
            _state.value = state.value.copy(maxLumen = luma)
        }
        if (luma < state.value.minLumen){
            _state.value = state.value.copy(minLumen = luma)
        }
    }

    fun resetLuminosityThreshold(){
        _state.value = state.value.copy(maxLumen = 0.0)
        _state.value = state.value.copy(minLumen = 250.0)
    }

    /////////////////CHANNELS//////////////////////////
    fun receiveSignal_Channel(channelIndex: Int, signal: String){
        val channel = state.value.channels[channelIndex]

        _state.value = state.value.copy(currentSignal = signal)
        if (signal == channel.lastSignal){
            return
        }
        else {
            updateLastSignal_Channel(channelIndex, signal)
        }
    }

    private fun updateLastSignal_Channel(channelIndex: Int, signal: String) {
        val currentTime = System.currentTimeMillis()
        val channels = state.value.channels.toMutableList()
        val lastSignalTime = currentTime - channels[channelIndex].lastSignalTime
        var message = channels[channelIndex].message.toMutableList()
        //println(state.value.lastSignalTime)
        if (lastSignalTime > morseUnit.dashLength.last+morseUnit.errorRate){
            if (message.isEmpty()){
                //message.add(signal)
            }
            else {
                if (signal == "HIGH" && lastSignalTime in morseUnit.spaceLength){
                    decodeArray_Channel(channelIndex)
                    message = mutableListOf()
                    message.add("PAUSE")
                }
                else {
                    println("Not a valid Morse symbol!!!! $signal $lastSignalTime")

                    decodeArray_Channel(channelIndex)
                    message = mutableListOf()

                    //message.removeLast()
                    //message.add(signal)
                }
            }
        }
        else{
            //message.add(signal)
            println(lastSignalTime)
            if (signal == "LOW" && lastSignalTime in morseUnit.dotLength) message.add("DOT")
            if (signal == "LOW" && lastSignalTime in morseUnit.dashLength) message.add("DASH")
            if (signal == "HIGH" && lastSignalTime in morseUnit.letterBrakeLength){
                decodeArray_Channel(channelIndex)
                message = mutableListOf()
            }

            //else return
            //Think about LOWsgnals
        }

        val logs: String = if (isLogging){
            "${state.value.log_text}\n$signal $lastSignalTime"
        } else {
            state.value.log_text
        }
        //TODO: why words doenst autoupdate
        val words = state.value.channels[channelIndex].words
        channels[channelIndex] = channels[channelIndex].copy(lastSignal = signal, lastSignalTime = currentTime, message = message, words = words)

        _state.value = state.value.copy(
            channels = channels,
            log_text = logs
        )
        println("Words in Update: ${ state.value.channels[0].words }")
    }
    private fun decodeArray_Channel(channelIndex: Int) {
        val alphabet = mapOf<String, Char>(
            ".-" to 'A',
            "-..." to 'B',
            "-.-." to 'C',
            "-.." to 'D',
            "." to 'E',
            "..-." to 'F',
            "--." to 'G',
            "...." to 'H',
            ".." to 'I',
            ".---" to 'J',
            "-.-" to 'K',
            ".-.." to 'L',
            "--" to 'M',
            "-." to 'N',
            "---" to 'O',
            ".--." to 'P',
            "--.-" to 'Q',
            ".-." to 'R',
            "..." to 'S',
            "-" to 'T',
            "..-" to 'U',
            "...-" to 'V',
            ".--" to 'W',
            "-..-" to 'X',
            "-.--" to 'Y',
            "--.." to 'Z',
            ".----" to '1',
            "..---" to '2',
            "...--" to '3',
            "....-" to '4',
            "....." to '5',
            "-...." to '6',
            "--..." to '7',
            "---.." to '8',
            "----." to '9',
            "-----" to '0'
        )
        val channels = state.value.channels.toMutableList()
        val words = channels[channelIndex].words.toMutableList()
        println("message to decode ${channels[channelIndex].message.joinToString()}")
        val chars: MutableList<Char> = mutableListOf()
//        if (channels[channelIndex].message.size == 1 && channels[channelIndex].message[0] == "PAUSE"){
//            words.add(" ")
//        }
//        else{
            for (word in channels[channelIndex].message){
                if (word == "DOT") chars.add('.')
                if (word == "DASH") chars.add('-')
                if (word == "PAUSE") words.add(" ")
            }
            val letterMorse = chars.joinToString(separator = "")
            words.add(alphabet[letterMorse].toString())
        //}
        channels[channelIndex] = channels[channelIndex].copy(words = words)
        _state.value = state.value.copy(channels = channels)
        println("Message: ${ state.value.channels[0].message }")
        println("Words: ${ state.value.channels[0].words }")
        //TODO("Decoding symbols row into alphabet")
    }
    fun addNewChannel(): Boolean{
        if (state.value.channels.size == 3){
            return false
        }
        val input = Offset(IntRange(0,480).random().toFloat(),IntRange(0,640).random().toFloat())
        val image = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)
        val newChannel = Channel(input, image)
        val channels = state.value.channels.toMutableList()
        channels.add(newChannel)
        _state.value = state.value.copy(channels = channels)
        return true
    }

    fun updateInputSources_Channel(channelIndex: Int, inputSource: Offset) {
        val channels = state.value.channels.toMutableList()
        channels[channelIndex] = channels[channelIndex].copy(inputSource = inputSource)
        //inputs.add(Offset(0f,0f))
        _state.value = state.value.copy(channels = channels)
    }

    fun updateInputImage_Channel(channelIndex:Int, updatedImage: Bitmap?) {
        val channels = state.value.channels.toMutableList()
        if (updatedImage != null){
            channels[channelIndex] = channels[channelIndex].copy(inputImage = updatedImage)
        }
        _state.value = state.value.copy(channels = channels)
    }

    fun updatePermission(perm: Boolean){
        _state.value = state.value.copy(permission = perm)
    }
}