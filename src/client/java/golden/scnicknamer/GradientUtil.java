package golden.scnicknamer;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.List;

/**
 * Utility class for applying gradient colors to text.
 */
public class GradientUtil {

    /**
     * Applies a gradient to the given text using the specified colors.
     * Expects the number of colors to be less than the number of characters in the text.
     *
     * @param text   The text to apply the gradient to.
     * @param colors An array of colors in integer format (0xRRGGBB).
     * @return A new Text object with the gradient applied.
     */
    public static Text applyGradient(Text text, int[] colors) {
        String str = text.getString();
        Style style = text.getStyle();
        int length = str.length();

        if (length < 1 || colors == null || colors.length < 1) {    // No text or no colors
            return text;
        }
        if (length == 1 || colors.length == 1) {    // Single character or single color
            return Text.literal(str).setStyle(style.withColor(colors[0]));
        }

        MutableText result = Text.empty();
        int segments = colors.length - 1;

        for (int i = 0; i < length; i++) {
            float pos = (float) i / (length - 1);
            int seg = (int) (pos * segments);
            float localRatio = pos * segments - seg;

            int color = interpolateColours(colors[seg], colors[seg + 1], localRatio);
            result.append(Text.literal(String.valueOf(str.charAt(i))).setStyle(style.withColor(color)));
        }
        return result;
    }

    /**
     * Applies a gradient to the given text using the specified hex color strings.
     *
     * @param text      The text to apply the gradient to.
     * @param hexColors A list of colors in hex string format.
     * @return A new Text object with the gradient applied.
     * @see #applyGradient(Text, int[])
     */
    public static Text applyGradient(Text text, List<String> hexColors) {
        int[] colorInts = hexColors.stream()
                .mapToInt(c -> Integer.parseInt(c, 16))
                .toArray();

        return applyGradient(text, colorInts);
    }

    /**
     * Helper method to interpolate between two colors.
     * @param start Start color in 0xRRGGBB format
     * @param end End color in 0xRRGGBB format
     * @param ratio Ratio between 0 and 1
     * @return Interpolated color in 0xRRGGBB format
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
