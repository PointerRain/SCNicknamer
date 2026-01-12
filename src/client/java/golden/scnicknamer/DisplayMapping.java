package golden.scnicknamer;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record DisplayMapping(String mc_name, UUID mc_uuid,
                             String nickname, String colour, String[] colours) {

    @Override @NotNull
    public String toString() {
        return "DisplayMapping{" +
                "mc_name='" + mc_name + '\'' +
                ", mc_uuid=" + mc_uuid +
                ", nickname=" + nickname +
                ", colour=" + colour +
                ", colours=" + String.join(",", colours) +
                '}';
    }

    @Override
    public int hashCode() {
        return mc_uuid != null ? mc_uuid.hashCode() : (mc_name != null ? mc_name.hashCode() : 0);
    }
}
