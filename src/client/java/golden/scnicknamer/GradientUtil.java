package golden.scnicknamer;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

import java.util.List;

/**
 * Utility class for applying gradient colours to text.
 */
public class GradientUtil {

    /**
     * Applies a gradient to the given text using the specified colours.
     * Expects the number of colours to be less than the number of characters in the text.
     *
     * @param text   The text to apply the gradient to.
     * @param colours An array of colours in integer format (0xRRGGBB).
     * @return A new Text object with the gradient applied.
     */
    public static Component applyGradient(Component text, int[] colours) {
        String str = text.getString();
        Style style = text.getStyle();
        int length = str.length();

        if (length < 1 || colours == null || colours.length < 1) {    // No text or no colours
            return text;
        }
        if (length == 1 || colours.length == 1) {    // Single character or single colour
            return Component.literal(str).setStyle(style.withColor(colours[0]));
        }

        MutableComponent result = Component.empty();
        int segments = colours.length - 1;

        for (int i = 0; i < length; i++) {
            float pos = (float) i / (length - 1);
            int seg = Math.min((int) (pos * segments), segments - 1);
            float localRatio = pos * segments - seg;

            int colour = interpolateColours(colours[seg], colours[seg + 1], localRatio);
            result.append(Component.literal(String.valueOf(str.charAt(i))).setStyle(style.withColor(colour)));
        }
        return result;
    }

    /**
     * Applies a gradient to the given text using the specified hex colour strings.
     *
     * @param text      The text to apply the gradient to.
     * @param hexColours A list of colours in hex string format.
     * @return A new Text object with the gradient applied.
     * @see #applyGradient(Component, int[])
     */
    public static Component applyGradient(Component text, List<String> hexColours) {
        int[] colourInts = hexColours.stream()
                .mapToInt(c -> Integer.parseInt(c, 16))
                .toArray();

        return applyGradient(text, colourInts);
    }

    /**
     * Helper method to interpolate between two colours.
     * @param start Start colour in 0xRRGGBB format
     * @param end End colour in 0xRRGGBB format
     * @param ratio Ratio between 0 and 1
     * @return Interpolated colour in 0xRRGGBB format
     */
    private static int interpolateColours(int start, int end, float ratio) {
        int r = interpolate(start >> 16 & 0xFF, end >> 16 & 0xFF, ratio);
        int g = interpolate(start >> 8 & 0xFF, end >> 8 & 0xFF, ratio);
        int b = interpolate(start & 0xFF, end & 0xFF, ratio);

        return (r << 16) | (g << 8) | b;
    }

    /**
     * Helper method to interpolate between two integer values.
     * @param start The start value
     * @param end The end value
     * @param ratio Ratio between 0 and 1
     * @return Interpolated integer value
     */
    private static int interpolate(int start, int end, float ratio) {
        return (int) (start + (end - start) * ratio);
    }
}