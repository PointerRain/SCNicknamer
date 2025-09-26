package golden.scnicknamer;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public record DisplayMapping(String mc_name, UUID mc_uuid, String discord_nick, String colour) {

    @Override @NotNull
    public String toString() {
        return "DisplayMapping{" +
                "mc_name='" + mc_name + '\'' +
                ", mc_uuid=" + mc_uuid +
                ", discord_nick=" + discord_nick +
                ", colour=" + colour +
                '}';
    }

    @Override
    public int hashCode() {
        return mc_uuid != null ? mc_uuid.hashCode() : (mc_name != null ? mc_name.hashCode() : 0);
    }
}
