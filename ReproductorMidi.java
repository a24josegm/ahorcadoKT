import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.Synthesizer;

class ReproductorMidi {

    Sequencer sequencer;
    Synthesizer synthesizer;

    public ReproductorMidi(String ficheroMidi) throws Exception {
        // simplificamos el throws con Exception para hacer código limpio (aunque de peor calidad)
        sequencer = MidiSystem.getSequencer();
        sequencer.open();

        // Verificamos si tenemos un sintetizador para controlar el volumen
        if (sequencer instanceof Synthesizer) {
            synthesizer = (Synthesizer) sequencer;
        } else {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
        }

        InputStream is = new BufferedInputStream(new FileInputStream(new File(ficheroMidi)));
        sequencer.setSequence(is);
        sequencer.start();
        sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
    }

    public void subirVolumen(int incremento) {
        if (synthesizer != null) {
            MidiChannel[] canales = synthesizer.getChannels();
            for (MidiChannel canal : canales) {
                if (canal != null) {
                    int volumenActual = canal.getController(7); // Controlador 7 = volumen principal
                    int nuevoVolumen = Math.min(volumenActual + incremento, 127); // El volumen máximo en MIDI es 127
                    canal.controlChange(7, nuevoVolumen);
                }
            }
        } else {
            System.out.println("No se puede ajustar el volumen. El sintetizador no está disponible.");
        }
    }

    void cerrar() {
        if (synthesizer != null) {
            synthesizer.close();
        }
        sequencer.close();
    }
}
