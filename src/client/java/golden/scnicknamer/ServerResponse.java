package golden.scnicknamer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record ServerResponse(Map<UUID, DisplayMapping> mappings, List<String> whitelist,
                             List<String> blacklist, List<String> messages) {
    public static final ServerResponse EMPTY = new ServerResponse(Map.of(), List.of(), List.of(), List.of());
}
