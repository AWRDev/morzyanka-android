package com.awrdev.cameracomposetest

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.awrdev.cameracomposetest.common.*

class MainViewModel: ViewModel() {

    private val _state = mutableStateOf(MainState())
    val state: State<MainState> = _state




    fun receiveSignal(signal: String){
        _state.value = state.value.copy(currentSignal = signal)
        if (signal == state.value.lastSignal){
            return
        }
        else {
            updateLastSignal(signal)
        }
    }

    private fun updateLastSignal(signal: String) {
        val currentTime = System.currentTimeMillis()
        val lastSignalTime = currentTime - state.value.lastSignalTime
        var message = state.value.message.toMutableList()
        println(state.value.lastSignalTime)
        if (lastSignalTime > DASH_LENGTH.last+ERROR_RATE){
            if (message.isEmpty()){
                //message.add(signal)
            }
            else {
                if (signal == "HIGH" && lastSignalTime in SPACE_LENGTH) message.add("PAUSE")
                else {
                    println("Not a valid Morse symbol!!!! $signal $lastSignalTime")

                    decodeArray()
                    message = mutableListOf()

                    //message.removeLast()
                    //message.add(signal)
                }
            }
        }
        else{
            //message.add(signal)
            println(lastSignalTime)
            if (signal == "LOW" && lastSignalTime in DOT_LENGTH) message.add("DOT")
            if (signal == "LOW" && lastSignalTime in DASH_LENGTH) message.add("DASH")
            if (signal == "HIGH" && lastSignalTime in LETTER_BRAKE_LENGTH){
                decodeArray()
                message = mutableListOf()
            }

                //else return
            //Think about LOWsgnals
        }

        _state.value = state.value.copy(
            lastSignal = signal,
            lastSignalTime = currentTime,
            message = message,
            log_text = "${state.value.log_text}\n$signal $lastSignalTime"
        )
    }

    private fun decodeArray() {
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
        println("message to decode ${state.value.message.joinToString()}")
        val chars: MutableList<Char> = mutableListOf()
        for (word in state.value.message){
            if (word == "DOT") chars.add('.')
            if (word == "DASH") chars.add('-')
        }
        val letterMorse = chars.joinToString(separator = "")
        val words = state.value.words.toMutableList()
        words.add(alphabet[letterMorse].toString())
        _state.value = state.value.copy(words = words)
        //TODO("Decoding symbols row into alphabet")
    }

    fun clearMessage(){
        _state.value = state.value.copy(message = emptyList())
    }

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

    fun updateColor(){
        val words = state.value.words.toMutableList()
        words.add(CharRange('A', 'Z').random().toString())
        _state.value = state.value.copy(words = words)
    }

    fun updateInputSources(inputSource: Offset) {
        val inputs = state.value.inputSources.toMutableList()
        inputs[0] = inputSource
        //inputs.add(Offset(0f,0f))
        _state.value = state.value.copy(inputSources = inputs)
    }

}