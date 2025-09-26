en_au = {
    "text.autoconfig.scnicknamer.title": "SCNicknamer",
    "text.autoconfig.scnicknamer.option.enableMod": "Enable Mod",
    "text.autoconfig.scnicknamer.option.apiLink": "API Link",
    "text.autoconfig.scnicknamer.option.replacetablist": "Nicknames in the tablist",
    "text.autoconfig.scnicknamer.option.colourtablist": "Colour names in the tablist",
    "text.autoconfig.scnicknamer.option.replacenametag": "Nicknames in nametags",
    "text.autoconfig.scnicknamer.option.colournametag": "Colour player nametags",
    "text.autoconfig.scnicknamer.option.replacechat": "Nicknames in chat",
    "text.autoconfig.scnicknamer.option.colourchat": "Colour names in chat",
    "text.autoconfig.scnicknamer.option.locatorbar": "Colour icons in the locator bar",

    "text.autoconfig.scnicknamer.option.enableMod.@Tooltip": "Game should be restarted after enabling.",
    "text.autoconfig.scnicknamer.option.apiLink.@Tooltip[0]": "Leave blank for Spooncraft.",
    "text.autoconfig.scnicknamer.option.apiLink.@Tooltip[1]": "Game should be restarted after changing.",

    "text.scnicknamer.status.disabled": "SCNicknamer is disabled.",
    "text.scnicknamer.status.working": "SCNicknamer has not finished setting up yet.",
    "text.scnicknamer.status.fallback": "SCNicknamer could not reach the server. Using cached fallback.",
    "text.scnicknamer.status.failure": "SCNicknamer could not reach the server or find a fallback.",
    "text.scnicknamer.status.success": "Loaded mappings from the server.",

    "gui.scnicknamer.hover_nickname": "Nickname: %s",

    "modmenu.nameTranslation.scnicknamer": "SCNicknamer",
    "modmenu.descriptionTranslation.scnicknamer": "Lightweight client-side nickname and colour mod."
}

en_us = en_au.copy()
en_us.update({
    "text.autoconfig.scnicknamer.option.colourtablist": "Color names in the tablist",
    "text.autoconfig.scnicknamer.option.colournametag": "Color player nametags",
    "text.autoconfig.scnicknamer.option.colourchat": "Color names in chat",
    "text.autoconfig.scnicknamer.option.locatorbar": "Color icons in the locator bar",

    "modmenu.descriptionTranslation.scnicknamer": "Lightweight client-side nickname and color mod.",
})

en_gb = en_au.copy()
en_gb.update({})

en_nz = en_au.copy()
en_nz.update({})

en_ca = en_au.copy()
en_ca.update({})

enp = en_au.copy()
enp.update({
    "text.autoconfig.scnicknamer.option.enableMod": "Lay on wending",
    "text.autoconfig.scnicknamer.option.replacetablist": "Nicknames in the playerlist",
    "text.autoconfig.scnicknamer.option.colourtablist": "Hue names in the playerlist",
    "text.autoconfig.scnicknamer.option.replacenametag": "Nicknames in nametokens",
    "text.autoconfig.scnicknamer.option.colournametag": "Hue player nametokens",
    "text.autoconfig.scnicknamer.option.replacechat": "Nicknames in chat",
    "text.autoconfig.scnicknamer.option.colourchat": "Hue names in chat",
    "text.autoconfig.scnicknamer.option.locatorbar": "Hue dots in the finder band",

    "text.scnicknamer.status.disabled": "SCNicknamer is off.",
    "text.scnicknamer.status.working": "SCNicknamer has not fulcome setting up yet.",
    "text.scnicknamer.status.fallback": "SCNicknamer could not reach the outreckoner. Using stored fallback.",
    "text.scnicknamer.status.failure": "SCNicknamer could not reach the outreckoner or use stored fallback.",
    "text.scnicknamer.status.success": "Loaded becomings from the outreckoner.",

    "text.autoconfig.scnicknamer.option.enableMod.@Tooltip": "Fand edstarting your game after laying on.",
    "text.autoconfig.scnicknamer.option.apiLink.@Tooltip[0]": "Leave blank if unknowed.",
    "text.autoconfig.scnicknamer.option.apiLink.@Tooltip[1]": "Fand edstarting your game after wending.",

    "modmenu.descriptionTranslation.scnicknamer": "Lightweight software-side nickname and hueing mod."
})

replacements = {'.': '˙', 's': 's', 'g': 'ᵷ', 'n': 'u', 'i': 'ᴉ', 't': 'ʇ', 'e': 'ǝ', 'S': 'S', ' ': ' ', 'y': 'ʎ', 'l': 'ꞁ', 'b': 'q', 'c': 'ɔ', 'A': 'Ɐ', 'r': 'ɹ', 'o': 'o', 'a': 'ɐ', 'h': 'ɥ', 'P': 'Ԁ', '?': '¿', 'v': 'ʌ', 'N': 'N', 'k': 'ʞ', 'u': 'n', 'd': 'p', 'W': 'M', '!': '¡', 'f': 'ɟ', 'M': 'W', 'm': 'ɯ', 'D': 'ᗡ', 'R': 'ᴚ', 'E': 'Ǝ', 'p': 'd', 'I': 'I', 'C': 'Ɔ', 'w': 'ʍ', 'U': '∩', 'O': 'O', 'B': 'ᗺ', 'T': '⟘', 'q': 'b', 'K': 'Ʞ', '0': '0', '1': '⥝', '-': '-', 'z': 'z', 'G': '⅁', "'": ',', '3': 'Ɛ', 'H': 'H', '4': '߈', ')': '(', '(': ')', 'F': 'Ⅎ', '&': '⅋', 'J': 'Ր', 'x': 'x', 'L': 'Ꞁ', 'V': 'Ʌ', ':': ':', ',': "'", '8': '8', 'Y': '⅄', '"': '„', 'Q': 'Ꝺ', 'Z': 'Z', ']': '[', '>': '<', '2': 'ᘔ', ';': '⸵', '*': '*', '[': ']', '<': '>', '=': '=', 'X': 'X', '#': '#', '6': '9', '7': 'ㄥ', '`': '`'}
en_ud = {key: ''.join(replacements.get(char, char) for char in value)[::-1] for key, value in en_au.items()}

toki_pona = en_au.copy()
toki_pona.update({
    "text.autoconfig.scnicknamer.title": "ilo nimi Supunkala",
    "text.autoconfig.scnicknamer.option.enableMod": "o pali e ilo",
    "text.autoconfig.scnicknamer.option.apiLink": "len API",

    "text.autoconfig.scnicknamer.option.replacetablist": "nimi pi toki poka lon palisa lipu",
    "text.autoconfig.scnicknamer.option.colourtablist": "kule e nimi lon palisa",
    "text.autoconfig.scnicknamer.option.replacenametag": "nimi pi toki poka lon nimi lipu",
    "text.autoconfig.scnicknamer.option.colournametag": "kule e nimi pi jan",
    "text.autoconfig.scnicknamer.option.replacechat": "nimi pi toki poka lon toki",
    "text.autoconfig.scnicknamer.option.colourchat": "kule e nimi lon toki",
    "text.autoconfig.scnicknamer.option.locatorbar": "kule e ijo pi lukin lon",

    "text.scnicknamer.status.disabled": "ilo nimi Supunkala li pini.",
    "text.scnicknamer.status.working":  "ilo nimi Supunkala li awen pali.",
    "text.scnicknamer.status.fallback": "ilo nimi Supunkala li ken ala kama jo e ilo pi lawa tomo. o pana kepeken ilo kama sina.",
    "text.scnicknamer.status.failure":  "ilo nimi Supunkala li ken ala kama jo e ilo pi lawa tomo anu ilo kama.",
    "text.scnicknamer.status.success":  "ilo li kama tan ilo pi lawa tomo.",

    "text.autoconfig.scnicknamer.option.enableMod.@Tooltip": "o pini musi o open sin e musi.",
    "text.autoconfig.scnicknamer.option.apiLink.@Tooltip[0]": "o pana e ijo ala, la sina sona ala.",
    "text.autoconfig.scnicknamer.option.apiLink.@Tooltip[1]": "o pini musi o open sin e musi.",

    "gui.scnicknamer.hover_nickname": "nimi poka: %s",

    "modmenu.nameTranslation.scnicknamer": "ilo nimi Supunkala",
    "modmenu.descriptionTranslation.scnicknamer": "ilo pi nimi en kule lili",
})

pirate = en_au.copy()
pirate.update({
    "text.autoconfig.scnicknamer.option.enableMod": "Hoist the Mod",
    "text.autoconfig.scnicknamer.option.apiLink": "API Map o' Treasure",
    "text.autoconfig.scnicknamer.option.replacetablist": "Use Nicknames in the Crew List",
    "text.autoconfig.scnicknamer.option.colourtablist": "Colour Names in the Crew List",
    "text.autoconfig.scnicknamer.option.replacenametag": "Show Nicknames in Name Banners",
    "text.autoconfig.scnicknamer.option.colournametag": "Colour the Name Banners o' the Crew",
    "text.autoconfig.scnicknamer.option.replacechat": "Nicknames in the Pirate Chatter",
    "text.autoconfig.scnicknamer.option.colourchat": "Colour Names in the Pirate Chatter",
    "text.autoconfig.scnicknamer.option.locatorbar": "Colour Dots in the Locator bar",

    "text.scnicknamer.status.disabled": "SCNicknamer be struck down n’ disabled.",
    "text.scnicknamer.status.working": "SCNicknamer be still settin' sail fer setup.",
    "text.scnicknamer.status.fallback": "SCNicknamer failed to reach the server. Usin’ cached fallback charts.",
    "text.scnicknamer.status.failure": "SCNicknamer be lost at sea — no server or fallback to be found!",
    "text.scnicknamer.status.success": "Mappings loaded from the server’s hoard.",

    "text.autoconfig.scnicknamer.option.enableMod.@Tooltip": "Restart the game after hoistin’ this option.",
    "text.autoconfig.scnicknamer.option.apiLink.@Tooltip[0]": "Leave this map blank if ye don’t know the way.",
    "text.autoconfig.scnicknamer.option.apiLink.@Tooltip[1]": "Restart the game after chartin’ a new map.",

    "modmenu.descriptionTranslation.scnicknamer": "Lightheft ship-side nickname and colourin' mod.",
})


languages = {
    'en_au': en_au,
    'en_gb': en_gb,
    'en_us': en_us,
    'en_nz': en_nz,
    'en_ca': en_ca,
    'enp':   enp,
    'en_ud': en_ud,
    'tok':   toki_pona,
    'en_pt': pirate
}


for lang in languages:
    f = open("src/main/resources/assets/scnicknamer/lang/"+lang+".json", "w", encoding="utf-8")
    lang_dict = languages[lang]
    f.write("{\n")
    for n, key in enumerate(lang_dict):
        # print(f'{key}": "{lang_dict[key]}"')
        f.write(f'    "{key}": "{lang_dict[key]}"')
        f.write(",\n" if n < len(lang_dict)-1 else '\n}')
    f.close()
    print(f"Wrote language {lang}")