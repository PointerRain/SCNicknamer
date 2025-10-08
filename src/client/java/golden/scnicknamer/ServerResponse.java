package golden.scnicknamer;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public record ServerResponse(HashMap<UUID, DisplayMapping> mappings, List<String> whitelist,
                             List<String> blacklist, List<String> messages) {}
