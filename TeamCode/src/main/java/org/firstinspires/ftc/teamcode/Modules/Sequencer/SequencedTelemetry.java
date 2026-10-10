package org.firstinspires.ftc.teamcode.Modules.Sequencer;

//import androidx.core.util.Pair;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.sun.tools.javac.util.Pair;

import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class SequencedTelemetry extends TelemetryImpl {
    public Map<String, Pair<Long, String>> updates = new LinkedHashMap<>();

    public SequencedTelemetry(OpMode opMode) {
        super(opMode);
    }

    @Override
    public boolean update() {
        updates.forEach((s, o) ->
                super.addData(s,  o.snd + (o.fst > 1000 ? " (Old! "+o.snd+"ms)" : "")));

        return super.update();
    }
    /**
     *  Will always return null. If you want an alternative behavior I can do some reflection magic <br>
     *  but that is not recommended.
     * */
    @Override
    @Nullable
    public Item addData(@NotNull String s, @Nullable Object o) {
        Pair<Long, String> update = new Pair<>(System.currentTimeMillis(),(o != null ? o.toString() : "null"));
        updates.put(s, update);
        return null;
    }

    public void removeData(String s) {
        updates.remove(s);
    }
}
