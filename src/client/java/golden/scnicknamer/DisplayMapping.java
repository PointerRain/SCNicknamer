package golden.scnicknamer;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public record DisplayMapping(String mc_name, UUID mc_uuid,
                             String nickname, String colour, String[] colours) {

    @Override @NotNull
    public String toString() {
        return "DisplayMapping{" +
                "mc_name='" + mc_name + '\'' +
                ", mc_uuid=" + mc_uuid +
                ", nickname=" + nickname +
                ", colour=" + colour +
                '}';
    }

    @Override
    public int hashCode() {
        return mc_uuid != null ? mc_uuid.hashCode() : (mc_name != null ? mc_name.hashCode() : 0);
    }
}
