package com.msgilligan.keynote;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 */
public class NotePlayer {
    int note = 80;
    Synthesizer synth;
    MidiChannel channel;

    NotePlayer() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channel = synth.getChannels()[0];
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    void play() {
        channel.noteOn(note, 80);
    }

    void stop() {
        channel.noteOff(note, 80);
    }
}
