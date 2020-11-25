package dev.thunderegg.colormaps;

import java.util.Arrays;
import java.util.List;

import javafx.scene.paint.Color;

/**
 * Colormaps from matplotlib python library
 */
class ColorMaps {
        public static ColorMap binary = new SeperateRGBListColorMap("binary",
                        Arrays.asList(new SinglePoint(0., 1., 1.), new SinglePoint(1., 0., 0.)),
                        Arrays.asList(new SinglePoint(0., 1., 1.), new SinglePoint(1., 0., 0.)),
                        Arrays.asList(new SinglePoint(0., 1., 1.), new SinglePoint(1., 0., 0.)));

        public static ColorMap autumn = new SeperateRGBListColorMap("autumn",
                        Arrays.asList(new SinglePoint(0., 1.0, 1.0), new SinglePoint(1.0, 1.0, 1.0)),
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(1.0, 1.0, 1.0)),
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(1.0, 0., 0.)));

        public static ColorMap bone = new SeperateRGBListColorMap("bone",
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(0.746032, 0.652778, 0.652778),
                                        new SinglePoint(1.0, 1.0, 1.0)),
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(0.365079, 0.319444, 0.319444),
                                        new SinglePoint(0.746032, 0.777778, 0.777778), new SinglePoint(1.0, 1.0, 1.0)),
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(0.365079, 0.444444, 0.444444),
                                        new SinglePoint(1.0, 1.0, 1.0)));

        public static ColorMap cool = new SeperateRGBListColorMap("cool",
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(1.0, 1.0, 1.0)),
                        Arrays.asList(new SinglePoint(0., 1., 1.), new SinglePoint(1.0, 0., 0.)),
                        Arrays.asList(new SinglePoint(0., 1., 1.), new SinglePoint(1.0, 1., 1.)));

        public static ColorMap copper = new SeperateRGBListColorMap("copper",
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(0.809524, 1.000000, 1.000000),
                                        new SinglePoint(1.0, 1.0, 1.0)),
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(1.0, 0.7812, 0.7812)),
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(1.0, 0.4975, 0.4975)));

        private static double flag_red(double x) {
                return 0.75 * Math.sin((x * 31.5 + 0.25) * Math.PI) + 0.5;
        }

        private static double flag_green(double x) {
                return Math.sin(x * 31.5 * Math.PI);
        }

        private static double flag_blue(double x) {
                return 0.75 * Math.sin((x * 31.5 - 0.25) * Math.PI) + 0.5;
        }

        public static ColorMap flag = new FunctionColorMap("flag", ColorMaps::flag_red, ColorMaps::flag_green,
                        ColorMaps::flag_blue);

        private static double prism_red(double x) {
                return 0.75 * Math.sin((x * 20.9 + 0.25) * Math.PI) + 0.67;
        }

        private static double prism_green(double x) {
                return 0.75 * Math.sin((x * 20.9 - 0.25) * Math.PI) + 0.33;
        }

        private static double prism_blue(double x) {
                return -1.1 * Math.sin((x * 20.9) * Math.PI);
        }

        public static ColorMap prism = new FunctionColorMap("prism", ColorMaps::prism_red, ColorMaps::prism_green,
                        ColorMaps::prism_blue);

        /**
         * Helper function for generating picklable cubehelix colormaps.
         */
        private static double ch_helper(double gamma, double s, double r, double h, double p0, double p1, double x) {
                // Apply gamma factor to emphasise low or high intensity values
                double xg = Math.pow(x, gamma);
                // Calculate amplitude and angle of deviation from the black to white
                // diagonal in the plane of constant perceived intensity.
                double a = h * xg * (1 - xg) / 2;
                double phi = 2 * Math.PI * (s / 3 + r * x);
                return xg + a * (p0 * Math.cos(phi) + p1 * Math.sin(phi));
        }

        /**
         * Return custom data dictionary of new Point(r, g, b) conversion functions,
         * which can be used with :func:`register_cmap`, for the cubehelix color scheme.
         * 
         * Unlike most other color schemes cubehelix was designed by D.A. Green to be
         * monotonically increasing in terms of perceived brightness. Also, when printed
         * on a black and white postscript printer, the scheme results in a greyscale
         * with monotonically increasing brightness. This color scheme is named
         * cubehelix because the new Point(r, g, b) values produced can be visualised as
         * a squashed helix around the diagonal in the new Point(r, g, b) color cube.
         * 
         * For a unit color cube new Point(i.e. 3D coordinates for (r, g, b) each in the
         * range 0 to 1) the color scheme starts at new Point(r, g, b) = (0, 0, 0), i.e.
         * black, and finishes at new Point(r, g, b) = (1, 1, 1), i.e. white. For some
         * fraction x*, between 0 and 1, the color is the corresponding grey value at
         * that fraction along the black to white diagonal new Point(x, x, x) plus a
         * color element. This color element is calculated in a plane of constant
         * perceived intensity and controlled by the following parameters.
         * 
         * Parameters ---------- gamma : float, default: 1 Gamma factor emphasizing
         * either low intensity values new Point(gamma < 1), or high intensity values
         * new Point(gamma > 1). s : float, default: 0.5 new Point(purple) The starting
         * color. r : float, default: -1.5 The number of r, g, b rotations in color that
         * are made from the start to the end of the color scheme. The default of -1.5
         * corresponds to -> B -> G -> R -> B. h : float, default: 1 The hue, i.e. how
         * saturated the colors are. If this parameter is zero then the color scheme is
         * purely a greyscale.
         **/
        private static ColorMap cubehelix_function(double gamma, double s, double r, double h) {
                return new FunctionColorMap("cubehelix", (double x) -> ch_helper(gamma, s, r, h, -0.14861, 1.78277, x),
                                (double x) -> ch_helper(gamma, s, r, h, -0.29227, -0.90649, x),
                                (double x) -> ch_helper(gamma, s, r, h, 1.97294, 0.0, x));
        }

        public static ColorMap cubehelix = cubehelix_function(1.0, 0.5, -1.5, 1.0);

        public static ColorMap bwr = new ColorListColorMap("bwr", Arrays.asList(new Color(0.0, 0.0, 1.0, 1.0),
                        new Color(1.0, 1.0, 1.0, 1.0), new Color(1.0, 0.0, 0.0, 1.0)));
        public static ColorMap brg = new ColorListColorMap("brg", Arrays.asList(new Color(0.0, 0.0, 1.0, 1.0),
                        new Color(1.0, 0.0, 0.0, 1.0), new Color(0.0, 1.0, 0.0, 1.0)));

        // Gnuplot palette functions

        private static double g0(double x) {
                return 0;
        }

        private static double g1(double x) {
                return 0.5;
        }

        private static double g2(double x) {
                return 1;
        }

        private static double g3(double x) {
                return x;
        }

        private static double g4(double x) {
                return Math.pow(x, 2);
        }

        private static double g5(double x) {
                return Math.pow(x, 3);
        }

        private static double g6(double x) {
                return Math.pow(x, 4);
        }

        private static double g7(double x) {
                return Math.sqrt(x);
        }

        private static double g8(double x) {
                return Math.sqrt(Math.sqrt(x));
        }

        private static double g9(double x) {
                return Math.sin(x * Math.PI / 2);
        }

        private static double g10(double x) {
                return Math.cos(x * Math.PI / 2);
        }

        private static double g11(double x) {
                return Math.abs(x - 0.5);
        }

        private static double g12(double x) {
                return Math.pow((2 * x - 1), 2);
        }

        private static double g13(double x) {
                return Math.sin(x * Math.PI);
        }

        private static double g14(double x) {
                return Math.abs(Math.cos(x * Math.PI));
        }

        private static double g15(double x) {
                return Math.sin(x * 2 * Math.PI);
        }

        private static double g16(double x) {
                return Math.cos(x * 2 * Math.PI);
        }

        private static double g17(double x) {
                return Math.abs(Math.sin(x * 2 * Math.PI));
        }

        private static double g18(double x) {
                return Math.abs(Math.cos(x * 2 * Math.PI));
        }

        private static double g19(double x) {
                return Math.abs(Math.sin(x * 4 * Math.PI));
        }

        private static double g20(double x) {
                return Math.abs(Math.cos(x * 4 * Math.PI));
        }

        private static double g21(double x) {
                return 3 * x;
        }

        private static double g22(double x) {
                return 3 * x - 1;
        }

        private static double g23(double x) {
                return 3 * x - 2;
        }

        private static double g24(double x) {
                return Math.abs(3 * x - 1);
        }

        private static double g25(double x) {
                return Math.abs(3 * x - 2);
        }

        private static double g26(double x) {
                return (3 * x - 1) / 2;
        }

        private static double g27(double x) {
                return (3 * x - 2) / 2;
        }

        private static double g28(double x) {
                return Math.abs((3 * x - 1) / 2);
        }

        private static double g29(double x) {
                return Math.abs((3 * x - 2) / 2);
        }

        private static double g30(double x) {
                return x / 0.32 - 0.78125;
        }

        private static double g31(double x) {
                return 2 * x - 0.84;
        }

        private static double g32(double x) {
                double ret = 0;
                if (x < 0.25) {
                        ret = 4 * x;
                } else if ((x >= 0.25) && (x < 0.92)) {
                        ret = -2 * x + 1.84;
                } else {
                        ret = x / 0.08 - 11.5;
                }
                return ret;
        }

        private static double g33(double x) {
                return Math.abs(2 * x - 0.5);
        }

        private static double g34(double x) {
                return 2 * x;
        }

        private static double g35(double x) {
                return 2 * x - 0.5;
        }

        private static double g36(double x) {
                return 2 * x - 1;
        }

        public static ColorMap gnuplot = new FunctionColorMap("gnuplot", ColorMaps::g7, ColorMaps::g5, ColorMaps::g15);

        public static ColorMap gnuplot2 = new FunctionColorMap("gnuplot2", ColorMaps::g30, ColorMaps::g31,
                        ColorMaps::g32);

        public static ColorMap ocean = new FunctionColorMap("ocean", ColorMaps::g23, ColorMaps::g28, ColorMaps::g3);

        public static ColorMap afmhot = new FunctionColorMap("afmhot", ColorMaps::g34, ColorMaps::g35, ColorMaps::g36);

        public static ColorMap rainbow = new FunctionColorMap("rainbow", ColorMaps::g33, ColorMaps::g13,
                        ColorMaps::g10);

        public static ColorMap seismic = new ColorListColorMap("seismic",
                        Arrays.asList(new Color(0.0, 0.0, 0.3, 1.0), new Color(0.0, 0.0, 1.0, 1.0),
                                        new Color(1.0, 1.0, 1.0, 1.0), new Color(1.0, 0.0, 0.0, 1.0),
                                        new Color(0.5, 0.0, 0.0, 1.0)));

        public static ColorMap terrain = new RGBPointListColorMap("terrain",
                        Arrays.asList(new RGBPoint(0.00, new Color(0.2, 0.2, 0.6, 1.0)),
                                        new RGBPoint(0.15, new Color(0.0, 0.6, 1.0, 1.0)),
                                        new RGBPoint(0.25, new Color(0.0, 0.8, 0.4, 1.0)),
                                        new RGBPoint(0.50, new Color(1.0, 1.0, 0.6, 1.0)),
                                        new RGBPoint(0.75, new Color(0.5, 0.36, 0.33, 1.0)),
                                        new RGBPoint(1.00, new Color(1.0, 1.0, 1.0, 1.0))));

        public static ColorMap gray = new SeperateRGBListColorMap("gray",
                        Arrays.asList(new SinglePoint(0., 0, 0), new SinglePoint(1., 1, 1)),
                        Arrays.asList(new SinglePoint(0., 0, 0), new SinglePoint(1., 1, 1)),
                        Arrays.asList(new SinglePoint(0., 0, 0), new SinglePoint(1., 1, 1)));

        public static ColorMap hot = new SeperateRGBListColorMap("hot",
                        Arrays.asList(new SinglePoint(0., 0.0416, 0.0416),
                                        new SinglePoint(0.365079, 1.000000, 1.000000), new SinglePoint(1.0, 1.0, 1.0)),
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(0.365079, 0.000000, 0.000000),
                                        new SinglePoint(0.746032, 1.000000, 1.000000), new SinglePoint(1.0, 1.0, 1.0)),
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(0.746032, 0.000000, 0.000000),
                                        new SinglePoint(1.0, 1.0, 1.0)));

        public static ColorMap hsv = new SeperateRGBListColorMap("hsv", Arrays.asList(new SinglePoint(0., 1., 1.),
                        new SinglePoint(0.158730, 1.000000, 1.000000), new SinglePoint(0.174603, 0.968750, 0.968750),
                        new SinglePoint(0.333333, 0.031250, 0.031250), new SinglePoint(0.349206, 0.000000, 0.000000),
                        new SinglePoint(0.666667, 0.000000, 0.000000), new SinglePoint(0.682540, 0.031250, 0.031250),
                        new SinglePoint(0.841270, 0.968750, 0.968750), new SinglePoint(0.857143, 1.000000, 1.000000),
                        new SinglePoint(1.0, 1.0, 1.0)),
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(0.158730, 0.937500, 0.937500),
                                        new SinglePoint(0.174603, 1.000000, 1.000000),
                                        new SinglePoint(0.507937, 1.000000, 1.000000),
                                        new SinglePoint(0.666667, 0.062500, 0.062500),
                                        new SinglePoint(0.682540, 0.000000, 0.000000), new SinglePoint(1.0, 0., 0.)),
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(0.333333, 0.000000, 0.000000),
                                        new SinglePoint(0.349206, 0.062500, 0.062500),
                                        new SinglePoint(0.507937, 1.000000, 1.000000),
                                        new SinglePoint(0.841270, 1.000000, 1.000000),
                                        new SinglePoint(0.857143, 0.937500, 0.937500),
                                        new SinglePoint(1.0, 0.09375, 0.09375)));

        public static ColorMap jet = new SeperateRGBListColorMap("jet",
                        Arrays.asList(new SinglePoint(0.00, 0, 0), new SinglePoint(0.35, 0, 0),
                                        new SinglePoint(0.66, 1, 1), new SinglePoint(0.89, 1, 1),
                                        new SinglePoint(1.00, 0.5, 0.5)),
                        Arrays.asList(new SinglePoint(0.000, 0, 0), new SinglePoint(0.125, 0, 0),
                                        new SinglePoint(0.375, 1, 1), new SinglePoint(0.640, 1, 1),
                                        new SinglePoint(0.910, 0, 0), new SinglePoint(1.000, 0, 0)),
                        Arrays.asList(new SinglePoint(0.00, 0.5, 0.5), new SinglePoint(0.11, 1, 1),
                                        new SinglePoint(0.34, 1, 1), new SinglePoint(0.65, 0, 0),
                                        new SinglePoint(1.00, 0, 0)));

        public static ColorMap pink = new SeperateRGBListColorMap("pink", Arrays.asList(
                        new SinglePoint(0., 0.1178, 0.1178), new SinglePoint(0.015873, 0.195857, 0.195857),
                        new SinglePoint(0.031746, 0.250661, 0.250661), new SinglePoint(0.047619, 0.295468, 0.295468),
                        new SinglePoint(0.063492, 0.334324, 0.334324), new SinglePoint(0.079365, 0.369112, 0.369112),
                        new SinglePoint(0.095238, 0.400892, 0.400892), new SinglePoint(0.111111, 0.430331, 0.430331),
                        new SinglePoint(0.126984, 0.457882, 0.457882), new SinglePoint(0.142857, 0.483867, 0.483867),
                        new SinglePoint(0.158730, 0.508525, 0.508525), new SinglePoint(0.174603, 0.532042, 0.532042),
                        new SinglePoint(0.190476, 0.554563, 0.554563), new SinglePoint(0.206349, 0.576204, 0.576204),
                        new SinglePoint(0.222222, 0.597061, 0.597061), new SinglePoint(0.238095, 0.617213, 0.617213),
                        new SinglePoint(0.253968, 0.636729, 0.636729), new SinglePoint(0.269841, 0.655663, 0.655663),
                        new SinglePoint(0.285714, 0.674066, 0.674066), new SinglePoint(0.301587, 0.691980, 0.691980),
                        new SinglePoint(0.317460, 0.709441, 0.709441), new SinglePoint(0.333333, 0.726483, 0.726483),
                        new SinglePoint(0.349206, 0.743134, 0.743134), new SinglePoint(0.365079, 0.759421, 0.759421),
                        new SinglePoint(0.380952, 0.766356, 0.766356), new SinglePoint(0.396825, 0.773229, 0.773229),
                        new SinglePoint(0.412698, 0.780042, 0.780042), new SinglePoint(0.428571, 0.786796, 0.786796),
                        new SinglePoint(0.444444, 0.793492, 0.793492), new SinglePoint(0.460317, 0.800132, 0.800132),
                        new SinglePoint(0.476190, 0.806718, 0.806718), new SinglePoint(0.492063, 0.813250, 0.813250),
                        new SinglePoint(0.507937, 0.819730, 0.819730), new SinglePoint(0.523810, 0.826160, 0.826160),
                        new SinglePoint(0.539683, 0.832539, 0.832539), new SinglePoint(0.555556, 0.838870, 0.838870),
                        new SinglePoint(0.571429, 0.845154, 0.845154), new SinglePoint(0.587302, 0.851392, 0.851392),
                        new SinglePoint(0.603175, 0.857584, 0.857584), new SinglePoint(0.619048, 0.863731, 0.863731),
                        new SinglePoint(0.634921, 0.869835, 0.869835), new SinglePoint(0.650794, 0.875897, 0.875897),
                        new SinglePoint(0.666667, 0.881917, 0.881917), new SinglePoint(0.682540, 0.887896, 0.887896),
                        new SinglePoint(0.698413, 0.893835, 0.893835), new SinglePoint(0.714286, 0.899735, 0.899735),
                        new SinglePoint(0.730159, 0.905597, 0.905597), new SinglePoint(0.746032, 0.911421, 0.911421),
                        new SinglePoint(0.761905, 0.917208, 0.917208), new SinglePoint(0.777778, 0.922958, 0.922958),
                        new SinglePoint(0.793651, 0.928673, 0.928673), new SinglePoint(0.809524, 0.934353, 0.934353),
                        new SinglePoint(0.825397, 0.939999, 0.939999), new SinglePoint(0.841270, 0.945611, 0.945611),
                        new SinglePoint(0.857143, 0.951190, 0.951190), new SinglePoint(0.873016, 0.956736, 0.956736),
                        new SinglePoint(0.888889, 0.962250, 0.962250), new SinglePoint(0.904762, 0.967733, 0.967733),
                        new SinglePoint(0.920635, 0.973185, 0.973185), new SinglePoint(0.936508, 0.978607, 0.978607),
                        new SinglePoint(0.952381, 0.983999, 0.983999), new SinglePoint(0.968254, 0.989361, 0.989361),
                        new SinglePoint(0.984127, 0.994695, 0.994695), new SinglePoint(1.0, 1.0, 1.0)),
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(0.015873, 0.102869, 0.102869),
                                        new SinglePoint(0.031746, 0.145479, 0.145479),
                                        new SinglePoint(0.047619, 0.178174, 0.178174),
                                        new SinglePoint(0.063492, 0.205738, 0.205738),
                                        new SinglePoint(0.079365, 0.230022, 0.230022),
                                        new SinglePoint(0.095238, 0.251976, 0.251976),
                                        new SinglePoint(0.111111, 0.272166, 0.272166),
                                        new SinglePoint(0.126984, 0.290957, 0.290957),
                                        new SinglePoint(0.142857, 0.308607, 0.308607),
                                        new SinglePoint(0.158730, 0.325300, 0.325300),
                                        new SinglePoint(0.174603, 0.341178, 0.341178),
                                        new SinglePoint(0.190476, 0.356348, 0.356348),
                                        new SinglePoint(0.206349, 0.370899, 0.370899),
                                        new SinglePoint(0.222222, 0.384900, 0.384900),
                                        new SinglePoint(0.238095, 0.398410, 0.398410),
                                        new SinglePoint(0.253968, 0.411476, 0.411476),
                                        new SinglePoint(0.269841, 0.424139, 0.424139),
                                        new SinglePoint(0.285714, 0.436436, 0.436436),
                                        new SinglePoint(0.301587, 0.448395, 0.448395),
                                        new SinglePoint(0.317460, 0.460044, 0.460044),
                                        new SinglePoint(0.333333, 0.471405, 0.471405),
                                        new SinglePoint(0.349206, 0.482498, 0.482498),
                                        new SinglePoint(0.365079, 0.493342, 0.493342),
                                        new SinglePoint(0.380952, 0.517549, 0.517549),
                                        new SinglePoint(0.396825, 0.540674, 0.540674),
                                        new SinglePoint(0.412698, 0.562849, 0.562849),
                                        new SinglePoint(0.428571, 0.584183, 0.584183),
                                        new SinglePoint(0.444444, 0.604765, 0.604765),
                                        new SinglePoint(0.460317, 0.624669, 0.624669),
                                        new SinglePoint(0.476190, 0.643958, 0.643958),
                                        new SinglePoint(0.492063, 0.662687, 0.662687),
                                        new SinglePoint(0.507937, 0.680900, 0.680900),
                                        new SinglePoint(0.523810, 0.698638, 0.698638),
                                        new SinglePoint(0.539683, 0.715937, 0.715937),
                                        new SinglePoint(0.555556, 0.732828, 0.732828),
                                        new SinglePoint(0.571429, 0.749338, 0.749338),
                                        new SinglePoint(0.587302, 0.765493, 0.765493),
                                        new SinglePoint(0.603175, 0.781313, 0.781313),
                                        new SinglePoint(0.619048, 0.796819, 0.796819),
                                        new SinglePoint(0.634921, 0.812029, 0.812029),
                                        new SinglePoint(0.650794, 0.826960, 0.826960),
                                        new SinglePoint(0.666667, 0.841625, 0.841625),
                                        new SinglePoint(0.682540, 0.856040, 0.856040),
                                        new SinglePoint(0.698413, 0.870216, 0.870216),
                                        new SinglePoint(0.714286, 0.884164, 0.884164),
                                        new SinglePoint(0.730159, 0.897896, 0.897896),
                                        new SinglePoint(0.746032, 0.911421, 0.911421),
                                        new SinglePoint(0.761905, 0.917208, 0.917208),
                                        new SinglePoint(0.777778, 0.922958, 0.922958),
                                        new SinglePoint(0.793651, 0.928673, 0.928673),
                                        new SinglePoint(0.809524, 0.934353, 0.934353),
                                        new SinglePoint(0.825397, 0.939999, 0.939999),
                                        new SinglePoint(0.841270, 0.945611, 0.945611),
                                        new SinglePoint(0.857143, 0.951190, 0.951190),
                                        new SinglePoint(0.873016, 0.956736, 0.956736),
                                        new SinglePoint(0.888889, 0.962250, 0.962250),
                                        new SinglePoint(0.904762, 0.967733, 0.967733),
                                        new SinglePoint(0.920635, 0.973185, 0.973185),
                                        new SinglePoint(0.936508, 0.978607, 0.978607),
                                        new SinglePoint(0.952381, 0.983999, 0.983999),
                                        new SinglePoint(0.968254, 0.989361, 0.989361),
                                        new SinglePoint(0.984127, 0.994695, 0.994695), new SinglePoint(1.0, 1.0, 1.0)),
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(0.015873, 0.102869, 0.102869),
                                        new SinglePoint(0.031746, 0.145479, 0.145479),
                                        new SinglePoint(0.047619, 0.178174, 0.178174),
                                        new SinglePoint(0.063492, 0.205738, 0.205738),
                                        new SinglePoint(0.079365, 0.230022, 0.230022),
                                        new SinglePoint(0.095238, 0.251976, 0.251976),
                                        new SinglePoint(0.111111, 0.272166, 0.272166),
                                        new SinglePoint(0.126984, 0.290957, 0.290957),
                                        new SinglePoint(0.142857, 0.308607, 0.308607),
                                        new SinglePoint(0.158730, 0.325300, 0.325300),
                                        new SinglePoint(0.174603, 0.341178, 0.341178),
                                        new SinglePoint(0.190476, 0.356348, 0.356348),
                                        new SinglePoint(0.206349, 0.370899, 0.370899),
                                        new SinglePoint(0.222222, 0.384900, 0.384900),
                                        new SinglePoint(0.238095, 0.398410, 0.398410),
                                        new SinglePoint(0.253968, 0.411476, 0.411476),
                                        new SinglePoint(0.269841, 0.424139, 0.424139),
                                        new SinglePoint(0.285714, 0.436436, 0.436436),
                                        new SinglePoint(0.301587, 0.448395, 0.448395),
                                        new SinglePoint(0.317460, 0.460044, 0.460044),
                                        new SinglePoint(0.333333, 0.471405, 0.471405),
                                        new SinglePoint(0.349206, 0.482498, 0.482498),
                                        new SinglePoint(0.365079, 0.493342, 0.493342),
                                        new SinglePoint(0.380952, 0.503953, 0.503953),
                                        new SinglePoint(0.396825, 0.514344, 0.514344),
                                        new SinglePoint(0.412698, 0.524531, 0.524531),
                                        new SinglePoint(0.428571, 0.534522, 0.534522),
                                        new SinglePoint(0.444444, 0.544331, 0.544331),
                                        new SinglePoint(0.460317, 0.553966, 0.553966),
                                        new SinglePoint(0.476190, 0.563436, 0.563436),
                                        new SinglePoint(0.492063, 0.572750, 0.572750),
                                        new SinglePoint(0.507937, 0.581914, 0.581914),
                                        new SinglePoint(0.523810, 0.590937, 0.590937),
                                        new SinglePoint(0.539683, 0.599824, 0.599824),
                                        new SinglePoint(0.555556, 0.608581, 0.608581),
                                        new SinglePoint(0.571429, 0.617213, 0.617213),
                                        new SinglePoint(0.587302, 0.625727, 0.625727),
                                        new SinglePoint(0.603175, 0.634126, 0.634126),
                                        new SinglePoint(0.619048, 0.642416, 0.642416),
                                        new SinglePoint(0.634921, 0.650600, 0.650600),
                                        new SinglePoint(0.650794, 0.658682, 0.658682),
                                        new SinglePoint(0.666667, 0.666667, 0.666667),
                                        new SinglePoint(0.682540, 0.674556, 0.674556),
                                        new SinglePoint(0.698413, 0.682355, 0.682355),
                                        new SinglePoint(0.714286, 0.690066, 0.690066),
                                        new SinglePoint(0.730159, 0.697691, 0.697691),
                                        new SinglePoint(0.746032, 0.705234, 0.705234),
                                        new SinglePoint(0.761905, 0.727166, 0.727166),
                                        new SinglePoint(0.777778, 0.748455, 0.748455),
                                        new SinglePoint(0.793651, 0.769156, 0.769156),
                                        new SinglePoint(0.809524, 0.789314, 0.789314),
                                        new SinglePoint(0.825397, 0.808969, 0.808969),
                                        new SinglePoint(0.841270, 0.828159, 0.828159),
                                        new SinglePoint(0.857143, 0.846913, 0.846913),
                                        new SinglePoint(0.873016, 0.865261, 0.865261),
                                        new SinglePoint(0.888889, 0.883229, 0.883229),
                                        new SinglePoint(0.904762, 0.900837, 0.900837),
                                        new SinglePoint(0.920635, 0.918109, 0.918109),
                                        new SinglePoint(0.936508, 0.935061, 0.935061),
                                        new SinglePoint(0.952381, 0.951711, 0.951711),
                                        new SinglePoint(0.968254, 0.968075, 0.968075),
                                        new SinglePoint(0.984127, 0.984167, 0.984167), new SinglePoint(1.0, 1.0, 1.0)));

        public static ColorMap spring = new SeperateRGBListColorMap("spring",
                        Arrays.asList(new SinglePoint(0., 1., 1.), new SinglePoint(1.0, 1.0, 1.0)),
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(1.0, 1.0, 1.0)),
                        Arrays.asList(new SinglePoint(0., 1., 1.), new SinglePoint(1.0, 0.0, 0.0)));

        public static ColorMap summer = new SeperateRGBListColorMap("summer",
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(1.0, 1.0, 1.0)),
                        Arrays.asList(new SinglePoint(0., 0.5, 0.5), new SinglePoint(1.0, 1.0, 1.0)),
                        Arrays.asList(new SinglePoint(0., 0.4, 0.4), new SinglePoint(1.0, 0.4, 0.4)));

        public static ColorMap winter = new SeperateRGBListColorMap("winter",
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(1.0, 0.0, 0.0)),
                        Arrays.asList(new SinglePoint(0., 0., 0.), new SinglePoint(1.0, 1.0, 1.0)),
                        Arrays.asList(new SinglePoint(0., 1., 1.), new SinglePoint(1.0, 0.5, 0.5)));

        public static ColorMap nipy_spectral = new SeperateRGBListColorMap("nipy_spectral",
                        Arrays.asList(new SinglePoint(0.0, 0.0, 0.0), new SinglePoint(0.05, 0.4667, 0.4667),
                                        new SinglePoint(0.10, 0.5333, 0.5333), new SinglePoint(0.15, 0.0, 0.0),
                                        new SinglePoint(0.20, 0.0, 0.0), new SinglePoint(0.25, 0.0, 0.0),
                                        new SinglePoint(0.30, 0.0, 0.0), new SinglePoint(0.35, 0.0, 0.0),
                                        new SinglePoint(0.40, 0.0, 0.0), new SinglePoint(0.45, 0.0, 0.0),
                                        new SinglePoint(0.50, 0.0, 0.0), new SinglePoint(0.55, 0.0, 0.0),
                                        new SinglePoint(0.60, 0.0, 0.0), new SinglePoint(0.65, 0.7333, 0.7333),
                                        new SinglePoint(0.70, 0.9333, 0.9333), new SinglePoint(0.75, 1.0, 1.0),
                                        new SinglePoint(0.80, 1.0, 1.0), new SinglePoint(0.85, 1.0, 1.0),
                                        new SinglePoint(0.90, 0.8667, 0.8667), new SinglePoint(0.95, 0.80, 0.80),
                                        new SinglePoint(1.0, 0.80, 0.80)),
                        Arrays.asList(new SinglePoint(0.0, 0.0, 0.0), new SinglePoint(0.05, 0.0, 0.0),
                                        new SinglePoint(0.10, 0.0, 0.0), new SinglePoint(0.15, 0.0, 0.0),
                                        new SinglePoint(0.20, 0.0, 0.0), new SinglePoint(0.25, 0.4667, 0.4667),
                                        new SinglePoint(0.30, 0.6000, 0.6000), new SinglePoint(0.35, 0.6667, 0.6667),
                                        new SinglePoint(0.40, 0.6667, 0.6667), new SinglePoint(0.45, 0.6000, 0.6000),
                                        new SinglePoint(0.50, 0.7333, 0.7333), new SinglePoint(0.55, 0.8667, 0.8667),
                                        new SinglePoint(0.60, 1.0, 1.0), new SinglePoint(0.65, 1.0, 1.0),
                                        new SinglePoint(0.70, 0.9333, 0.9333), new SinglePoint(0.75, 0.8000, 0.8000),
                                        new SinglePoint(0.80, 0.6000, 0.6000), new SinglePoint(0.85, 0.0, 0.0),
                                        new SinglePoint(0.90, 0.0, 0.0), new SinglePoint(0.95, 0.0, 0.0),
                                        new SinglePoint(1.0, 0.80, 0.80)),
                        Arrays.asList(new SinglePoint(0.0, 0.0, 0.0), new SinglePoint(0.05, 0.5333, 0.5333),
                                        new SinglePoint(0.10, 0.6000, 0.6000), new SinglePoint(0.15, 0.6667, 0.6667),
                                        new SinglePoint(0.20, 0.8667, 0.8667), new SinglePoint(0.25, 0.8667, 0.8667),
                                        new SinglePoint(0.30, 0.8667, 0.8667), new SinglePoint(0.35, 0.6667, 0.6667),
                                        new SinglePoint(0.40, 0.5333, 0.5333), new SinglePoint(0.45, 0.0, 0.0),
                                        new SinglePoint(0.5, 0.0, 0.0), new SinglePoint(0.55, 0.0, 0.0),
                                        new SinglePoint(0.60, 0.0, 0.0), new SinglePoint(0.65, 0.0, 0.0),
                                        new SinglePoint(0.70, 0.0, 0.0), new SinglePoint(0.75, 0.0, 0.0),
                                        new SinglePoint(0.80, 0.0, 0.0), new SinglePoint(0.85, 0.0, 0.0),
                                        new SinglePoint(0.90, 0.0, 0.0), new SinglePoint(0.95, 0.0, 0.0),
                                        new SinglePoint(1.0, 0.80, 0.80)));

        /**
         * 34 colormaps based on color specifications and designs developed by
         *
         * Cynthia Brewer new Point(http://colorbrewer.org).
         *
         * The ColorBrewer palettes have been included under the terms of an
         * Apache-stype license new Point(for details, see the file LICENSE_COLORBREWER
         * in the license directory of the matplotlib source distribution).
         *
         * RGB values taken from Brewer's Excel sheet, divided by 255
         */
        public static ColorMap Blues = new ColorListColorMap("Blues",
                        Arrays.asList(new Color(0.96862745098039216, 0.98431372549019602, 1.0, 1.0),
                                        new Color(0.87058823529411766, 0.92156862745098034, 0.96862745098039216, 1.0),
                                        new Color(0.77647058823529413, 0.85882352941176465, 0.93725490196078431, 1.0),
                                        new Color(0.61960784313725492, 0.792156862745098, 0.88235294117647056, 1.0),
                                        new Color(0.41960784313725491, 0.68235294117647061, 0.83921568627450982, 1.0),
                                        new Color(0.25882352941176473, 0.5725490196078431, 0.77647058823529413, 1.0),
                                        new Color(0.12941176470588237, 0.44313725490196076, 0.70980392156862748, 1.0),
                                        new Color(0.03137254901960784, 0.31764705882352939, 0.61176470588235299, 1.0),
                                        new Color(0.03137254901960784, 0.18823529411764706, 0.41960784313725491, 1.0)));

        public static ColorMap BrBG = new ColorListColorMap("BrBG",
                        Arrays.asList(new Color(0.32941176470588235, 0.18823529411764706, 0.0196078431372549, 1.0),
                                        new Color(0.5490196078431373, 0.31764705882352939, 0.0392156862745098, 1.0),
                                        new Color(0.74901960784313726, 0.50588235294117645, 0.17647058823529413, 1.0),
                                        new Color(0.87450980392156863, 0.76078431372549016, 0.49019607843137253, 1.0),
                                        new Color(0.96470588235294119, 0.90980392156862744, 0.76470588235294112, 1.0),
                                        new Color(0.96078431372549022, 0.96078431372549022, 0.96078431372549022, 1.0),
                                        new Color(0.7803921568627451, 0.91764705882352937, 0.89803921568627454, 1.0),
                                        new Color(0.50196078431372548, 0.80392156862745101, 0.75686274509803919, 1.0),
                                        new Color(0.20784313725490197, 0.59215686274509804, 0.5607843137254902, 1.0),
                                        new Color(0.00392156862745098, 0.4, 0.36862745098039218, 1.0),
                                        new Color(0.0, 0.23529411764705882, 0.18823529411764706, 1.0)));

        public static ColorMap BuGn = new ColorListColorMap("BuGn",
                        Arrays.asList(new Color(0.96862745098039216, 0.9882352941176471, 0.99215686274509807, 1.0),
                                        new Color(0.89803921568627454, 0.96078431372549022, 0.97647058823529409, 1.0),
                                        new Color(0.8, 0.92549019607843142, 0.90196078431372551, 1.0),
                                        new Color(0.6, 0.84705882352941175, 0.78823529411764703, 1.0),
                                        new Color(0.4, 0.76078431372549016, 0.64313725490196083, 1.0),
                                        new Color(0.25490196078431371, 0.68235294117647061, 0.46274509803921571, 1.0),
                                        new Color(0.13725490196078433, 0.54509803921568623, 0.27058823529411763, 1.0),
                                        new Color(0.0, 0.42745098039215684, 0.17254901960784313, 1.0),
                                        new Color(0.0, 0.26666666666666666, 0.10588235294117647, 1.0)));

        public static ColorMap BuPu = new ColorListColorMap("BuPu",
                        Arrays.asList(new Color(0.96862745098039216, 0.9882352941176471, 0.99215686274509807, 1.0),
                                        new Color(0.8784313725490196, 0.92549019607843142, 0.95686274509803926, 1.0),
                                        new Color(0.74901960784313726, 0.82745098039215681, 0.90196078431372551, 1.0),
                                        new Color(0.61960784313725492, 0.73725490196078436, 0.85490196078431369, 1.0),
                                        new Color(0.5490196078431373, 0.58823529411764708, 0.77647058823529413, 1.0),
                                        new Color(0.5490196078431373, 0.41960784313725491, 0.69411764705882351, 1.0),
                                        new Color(0.53333333333333333, 0.25490196078431371, 0.61568627450980395, 1.0),
                                        new Color(0.50588235294117645, 0.05882352941176471, 0.48627450980392156, 1.0),
                                        new Color(0.30196078431372547, 0.0, 0.29411764705882354, 1.0)));

        public static ColorMap GnBu = new ColorListColorMap("GnBu",
                        Arrays.asList(new Color(0.96862745098039216, 0.9882352941176471, 0.94117647058823528, 1.0),
                                        new Color(0.8784313725490196, 0.95294117647058818, 0.85882352941176465, 1.0),
                                        new Color(0.8, 0.92156862745098034, 0.77254901960784317, 1.0),
                                        new Color(0.6588235294117647, 0.8666666666666667, 0.70980392156862748, 1.0),
                                        new Color(0.4823529411764706, 0.8, 0.7686274509803922, 1.0),
                                        new Color(0.30588235294117649, 0.70196078431372544, 0.82745098039215681, 1.0),
                                        new Color(0.16862745098039217, 0.5490196078431373, 0.74509803921568629, 1.0),
                                        new Color(0.03137254901960784, 0.40784313725490196, 0.67450980392156867, 1.0),
                                        new Color(0.03137254901960784, 0.25098039215686274, 0.50588235294117645, 1.0)));

        public static ColorMap Greens = new ColorListColorMap("Greens",
                        Arrays.asList(new Color(0.96862745098039216, 0.9882352941176471, 0.96078431372549022, 1.0),
                                        new Color(0.89803921568627454, 0.96078431372549022, 0.8784313725490196, 1.0),
                                        new Color(0.7803921568627451, 0.9137254901960784, 0.75294117647058822, 1.0),
                                        new Color(0.63137254901960782, 0.85098039215686272, 0.60784313725490191, 1.0),
                                        new Color(0.45490196078431372, 0.7686274509803922, 0.46274509803921571, 1.0),
                                        new Color(0.25490196078431371, 0.6705882352941176, 0.36470588235294116, 1.0),
                                        new Color(0.13725490196078433, 0.54509803921568623, 0.27058823529411763, 1.0),
                                        new Color(0.0, 0.42745098039215684, 0.17254901960784313, 1.0),
                                        new Color(0.0, 0.26666666666666666, 0.10588235294117647, 1.0)));

        public static ColorMap Greys = new ColorListColorMap("Greys",
                        Arrays.asList(new Color(1.0, 1.0, 1.0, 1.0),
                                        new Color(0.94117647058823528, 0.94117647058823528, 0.94117647058823528, 1.0),
                                        new Color(0.85098039215686272, 0.85098039215686272, 0.85098039215686272, 1.0),
                                        new Color(0.74117647058823533, 0.74117647058823533, 0.74117647058823533, 1.0),
                                        new Color(0.58823529411764708, 0.58823529411764708, 0.58823529411764708, 1.0),
                                        new Color(0.45098039215686275, 0.45098039215686275, 0.45098039215686275, 1.0),
                                        new Color(0.32156862745098042, 0.32156862745098042, 0.32156862745098042, 1.0),
                                        new Color(0.14509803921568629, 0.14509803921568629, 0.14509803921568629, 1.0),
                                        new Color(0.0, 0.0, 0.0, 1.0)));

        public static ColorMap Oranges = new ColorListColorMap("Oranges",
                        Arrays.asList(new Color(1.0, 0.96078431372549022, 0.92156862745098034, 1.0),
                                        new Color(0.99607843137254903, 0.90196078431372551, 0.80784313725490198, 1.0),
                                        new Color(0.99215686274509807, 0.81568627450980391, 0.63529411764705879, 1.0),
                                        new Color(0.99215686274509807, 0.68235294117647061, 0.41960784313725491, 1.0),
                                        new Color(0.99215686274509807, 0.55294117647058827, 0.23529411764705882, 1.0),
                                        new Color(0.94509803921568625, 0.41176470588235292, 0.07450980392156863, 1.0),
                                        new Color(0.85098039215686272, 0.28235294117647058, 0.00392156862745098, 1.0),
                                        new Color(0.65098039215686276, 0.21176470588235294, 0.01176470588235294, 1.0),
                                        new Color(0.49803921568627452, 0.15294117647058825, 0.01568627450980392, 1.0)));

        public static ColorMap OrRd = new ColorListColorMap("OrRd",
                        Arrays.asList(new Color(1.0, 0.96862745098039216, 0.92549019607843142, 1.0),
                                        new Color(0.99607843137254903, 0.90980392156862744, 0.78431372549019607, 1.0),
                                        new Color(0.99215686274509807, 0.83137254901960789, 0.61960784313725492, 1.0),
                                        new Color(0.99215686274509807, 0.73333333333333328, 0.51764705882352946, 1.0),
                                        new Color(0.9882352941176471, 0.55294117647058827, 0.34901960784313724, 1.0),
                                        new Color(0.93725490196078431, 0.396078431372549, 0.28235294117647058, 1.0),
                                        new Color(0.84313725490196079, 0.18823529411764706, 0.12156862745098039, 1.0),
                                        new Color(0.70196078431372544, 0.0, 0.0, 1.0),
                                        new Color(0.49803921568627452, 0.0, 0.0, 1.0)));

        public static ColorMap PiYG = new ColorListColorMap("PiYG",
                        Arrays.asList(new Color(0.55686274509803924, 0.00392156862745098, 0.32156862745098042, 1.0),
                                        new Color(0.77254901960784317, 0.10588235294117647, 0.49019607843137253, 1.0),
                                        new Color(0.87058823529411766, 0.46666666666666667, 0.68235294117647061, 1.0),
                                        new Color(0.94509803921568625, 0.71372549019607845, 0.85490196078431369, 1.0),
                                        new Color(0.99215686274509807, 0.8784313725490196, 0.93725490196078431, 1.0),
                                        new Color(0.96862745098039216, 0.96862745098039216, 0.96862745098039216, 1.0),
                                        new Color(0.90196078431372551, 0.96078431372549022, 0.81568627450980391, 1.0),
                                        new Color(0.72156862745098038, 0.88235294117647056, 0.52549019607843139, 1.0),
                                        new Color(0.49803921568627452, 0.73725490196078436, 0.25490196078431371, 1.0),
                                        new Color(0.30196078431372547, 0.5725490196078431, 0.12941176470588237, 1.0),
                                        new Color(0.15294117647058825, 0.39215686274509803, 0.09803921568627451, 1.0)));

        public static ColorMap PRGn = new ColorListColorMap("PRGn",
                        Arrays.asList(new Color(0.25098039215686274, 0.0, 0.29411764705882354, 1.0),
                                        new Color(0.46274509803921571, 0.16470588235294117, 0.51372549019607838, 1.0),
                                        new Color(0.6, 0.4392156862745098, 0.6705882352941176, 1.0),
                                        new Color(0.76078431372549016, 0.6470588235294118, 0.81176470588235294, 1.0),
                                        new Color(0.90588235294117647, 0.83137254901960789, 0.90980392156862744, 1.0),
                                        new Color(0.96862745098039216, 0.96862745098039216, 0.96862745098039216, 1.0),
                                        new Color(0.85098039215686272, 0.94117647058823528, 0.82745098039215681, 1.0),
                                        new Color(0.65098039215686276, 0.85882352941176465, 0.62745098039215685, 1.0),
                                        new Color(0.35294117647058826, 0.68235294117647061, 0.38039215686274508, 1.0),
                                        new Color(0.10588235294117647, 0.47058823529411764, 0.21568627450980393, 1.0),
                                        new Color(0.0, 0.26666666666666666, 0.10588235294117647, 1.0)));

        public static ColorMap PuBu = new ColorListColorMap("PuBu",
                        Arrays.asList(new Color(1.0, 0.96862745098039216, 0.98431372549019602, 1.0),
                                        new Color(0.92549019607843142, 0.90588235294117647, 0.94901960784313721, 1.0),
                                        new Color(0.81568627450980391, 0.81960784313725488, 0.90196078431372551, 1.0),
                                        new Color(0.65098039215686276, 0.74117647058823533, 0.85882352941176465, 1.0),
                                        new Color(0.45490196078431372, 0.66274509803921566, 0.81176470588235294, 1.0),
                                        new Color(0.21176470588235294, 0.56470588235294117, 0.75294117647058822, 1.0),
                                        new Color(0.0196078431372549, 0.4392156862745098, 0.69019607843137254, 1.0),
                                        new Color(0.01568627450980392, 0.35294117647058826, 0.55294117647058827, 1.0),
                                        new Color(0.00784313725490196, 0.2196078431372549, 0.34509803921568627, 1.0)));

        public static ColorMap PuBuGn = new ColorListColorMap("PuBuGn",
                        Arrays.asList(new Color(1.0, 0.96862745098039216, 0.98431372549019602, 1.0),
                                        new Color(0.92549019607843142, 0.88627450980392153, 0.94117647058823528, 1.0),
                                        new Color(0.81568627450980391, 0.81960784313725488, 0.90196078431372551, 1.0),
                                        new Color(0.65098039215686276, 0.74117647058823533, 0.85882352941176465, 1.0),
                                        new Color(0.40392156862745099, 0.66274509803921566, 0.81176470588235294, 1.0),
                                        new Color(0.21176470588235294, 0.56470588235294117, 0.75294117647058822, 1.0),
                                        new Color(0.00784313725490196, 0.50588235294117645, 0.54117647058823526, 1.0),
                                        new Color(0.00392156862745098, 0.42352941176470588, 0.34901960784313724, 1.0),
                                        new Color(0.00392156862745098, 0.27450980392156865, 0.21176470588235294, 1.0)));

        public static ColorMap PuOr = new ColorListColorMap("PuOr",
                        Arrays.asList(new Color(0.49803921568627452, 0.23137254901960785, 0.03137254901960784, 1.0),
                                        new Color(0.70196078431372544, 0.34509803921568627, 0.02352941176470588, 1.0),
                                        new Color(0.8784313725490196, 0.50980392156862742, 0.07843137254901961, 1.0),
                                        new Color(0.99215686274509807, 0.72156862745098038, 0.38823529411764707, 1.0),
                                        new Color(0.99607843137254903, 0.8784313725490196, 0.71372549019607845, 1.0),
                                        new Color(0.96862745098039216, 0.96862745098039216, 0.96862745098039216, 1.0),
                                        new Color(0.84705882352941175, 0.85490196078431369, 0.92156862745098034, 1.0),
                                        new Color(0.69803921568627447, 0.6705882352941176, 0.82352941176470584, 1.0),
                                        new Color(0.50196078431372548, 0.45098039215686275, 0.67450980392156867, 1.0),
                                        new Color(0.32941176470588235, 0.15294117647058825, 0.53333333333333333, 1.0),
                                        new Color(0.17647058823529413, 0.0, 0.29411764705882354, 1.0)));

        public static ColorMap PuRd = new ColorListColorMap("PuRd",
                        Arrays.asList(new Color(0.96862745098039216, 0.95686274509803926, 0.97647058823529409, 1.0),
                                        new Color(0.90588235294117647, 0.88235294117647056, 0.93725490196078431, 1.0),
                                        new Color(0.83137254901960789, 0.72549019607843135, 0.85490196078431369, 1.0),
                                        new Color(0.78823529411764703, 0.58039215686274515, 0.7803921568627451, 1.0),
                                        new Color(0.87450980392156863, 0.396078431372549, 0.69019607843137254, 1.0),
                                        new Color(0.90588235294117647, 0.16078431372549021, 0.54117647058823526, 1.0),
                                        new Color(0.80784313725490198, 0.07058823529411765, 0.33725490196078434, 1.0),
                                        new Color(0.59607843137254901, 0.0, 0.2627450980392157, 1.0),
                                        new Color(0.40392156862745099, 0.0, 0.12156862745098039, 1.0)));

        public static ColorMap Purples = new ColorListColorMap("Purples",
                        Arrays.asList(new Color(0.9882352941176471, 0.98431372549019602, 0.99215686274509807, 1.0),
                                        new Color(0.93725490196078431, 0.92941176470588238, 0.96078431372549022, 1.0),
                                        new Color(0.85490196078431369, 0.85490196078431369, 0.92156862745098034, 1.0),
                                        new Color(0.73725490196078436, 0.74117647058823533, 0.86274509803921573, 1.0),
                                        new Color(0.61960784313725492, 0.60392156862745094, 0.78431372549019607, 1.0),
                                        new Color(0.50196078431372548, 0.49019607843137253, 0.72941176470588232, 1.0),
                                        new Color(0.41568627450980394, 0.31764705882352939, 0.63921568627450975, 1.0),
                                        new Color(0.32941176470588235, 0.15294117647058825, 0.5607843137254902, 1.0),
                                        new Color(0.24705882352941178, 0.0, 0.49019607843137253, 1.0)));

        public static ColorMap RdBu = new ColorListColorMap("RdBu",
                        Arrays.asList(new Color(0.40392156862745099, 0.0, 0.12156862745098039, 1.0),
                                        new Color(0.69803921568627447, 0.09411764705882353, 0.16862745098039217, 1.0),
                                        new Color(0.83921568627450982, 0.37647058823529411, 0.30196078431372547, 1.0),
                                        new Color(0.95686274509803926, 0.6470588235294118, 0.50980392156862742, 1.0),
                                        new Color(0.99215686274509807, 0.85882352941176465, 0.7803921568627451, 1.0),
                                        new Color(0.96862745098039216, 0.96862745098039216, 0.96862745098039216, 1.0),
                                        new Color(0.81960784313725488, 0.89803921568627454, 0.94117647058823528, 1.0),
                                        new Color(0.5725490196078431, 0.77254901960784317, 0.87058823529411766, 1.0),
                                        new Color(0.2627450980392157, 0.57647058823529407, 0.76470588235294112, 1.0),
                                        new Color(0.12941176470588237, 0.4, 0.67450980392156867, 1.0),
                                        new Color(0.0196078431372549, 0.18823529411764706, 0.38039215686274508, 1.0)));

        public static ColorMap RdGy = new ColorListColorMap("RdGy",
                        Arrays.asList(new Color(0.40392156862745099, 0.0, 0.12156862745098039, 1.0),
                                        new Color(0.69803921568627447, 0.09411764705882353, 0.16862745098039217, 1.0),
                                        new Color(0.83921568627450982, 0.37647058823529411, 0.30196078431372547, 1.0),
                                        new Color(0.95686274509803926, 0.6470588235294118, 0.50980392156862742, 1.0),
                                        new Color(0.99215686274509807, 0.85882352941176465, 0.7803921568627451, 1.0),
                                        new Color(1.0, 1.0, 1.0, 1.0),
                                        new Color(0.8784313725490196, 0.8784313725490196, 0.8784313725490196, 1.0),
                                        new Color(0.72941176470588232, 0.72941176470588232, 0.72941176470588232, 1.0),
                                        new Color(0.52941176470588236, 0.52941176470588236, 0.52941176470588236, 1.0),
                                        new Color(0.30196078431372547, 0.30196078431372547, 0.30196078431372547, 1.0),
                                        new Color(0.10196078431372549, 0.10196078431372549, 0.10196078431372549, 1.0)));

        public static ColorMap RdPu = new ColorListColorMap("RdPu",
                        Arrays.asList(new Color(1.0, 0.96862745098039216, 0.95294117647058818, 1.0),
                                        new Color(0.99215686274509807, 0.8784313725490196, 0.86666666666666667, 1.0),
                                        new Color(0.9882352941176471, 0.77254901960784317, 0.75294117647058822, 1.0),
                                        new Color(0.98039215686274506, 0.62352941176470589, 0.70980392156862748, 1.0),
                                        new Color(0.96862745098039216, 0.40784313725490196, 0.63137254901960782, 1.0),
                                        new Color(0.86666666666666667, 0.20392156862745098, 0.59215686274509804, 1.0),
                                        new Color(0.68235294117647061, 0.00392156862745098, 0.49411764705882355, 1.0),
                                        new Color(0.47843137254901963, 0.00392156862745098, 0.46666666666666667, 1.0),
                                        new Color(0.28627450980392155, 0.0, 0.41568627450980394, 1.0)));

        public static ColorMap RdYlBu = new ColorListColorMap("RdYlBu",
                        Arrays.asList(new Color(0.6470588235294118, 0.0, 0.14901960784313725, 1.0),
                                        new Color(0.84313725490196079, 0.18823529411764706, 0.15294117647058825, 1.0),
                                        new Color(0.95686274509803926, 0.42745098039215684, 0.2627450980392157, 1.0),
                                        new Color(0.99215686274509807, 0.68235294117647061, 0.38039215686274508, 1.0),
                                        new Color(0.99607843137254903, 0.8784313725490196, 0.56470588235294117, 1.0),
                                        new Color(1.0, 1.0, 0.74901960784313726, 1.0),
                                        new Color(0.8784313725490196, 0.95294117647058818, 0.97254901960784312, 1.0),
                                        new Color(0.6705882352941176, 0.85098039215686272, 0.9137254901960784, 1.0),
                                        new Color(0.45490196078431372, 0.67843137254901964, 0.81960784313725488, 1.0),
                                        new Color(0.27058823529411763, 0.45882352941176469, 0.70588235294117652, 1.0),
                                        new Color(0.19215686274509805, 0.21176470588235294, 0.58431372549019611, 1.0)));

        public static ColorMap RdYlGn = new ColorListColorMap("RdYlGn",
                        Arrays.asList(new Color(0.6470588235294118, 0.0, 0.14901960784313725, 1.0),
                                        new Color(0.84313725490196079, 0.18823529411764706, 0.15294117647058825, 1.0),
                                        new Color(0.95686274509803926, 0.42745098039215684, 0.2627450980392157, 1.0),
                                        new Color(0.99215686274509807, 0.68235294117647061, 0.38039215686274508, 1.0),
                                        new Color(0.99607843137254903, 0.8784313725490196, 0.54509803921568623, 1.0),
                                        new Color(1.0, 1.0, 0.74901960784313726, 1.0),
                                        new Color(0.85098039215686272, 0.93725490196078431, 0.54509803921568623, 1.0),
                                        new Color(0.65098039215686276, 0.85098039215686272, 0.41568627450980394, 1.0),
                                        new Color(0.4, 0.74117647058823533, 0.38823529411764707, 1.0),
                                        new Color(0.10196078431372549, 0.59607843137254901, 0.31372549019607843, 1.0),
                                        new Color(0.0, 0.40784313725490196, 0.21568627450980393, 1.0)));

        public static ColorMap Reds = new ColorListColorMap("Reds",
                        Arrays.asList(new Color(1.0, 0.96078431372549022, 0.94117647058823528, 1.0),
                                        new Color(0.99607843137254903, 0.8784313725490196, 0.82352941176470584, 1.0),
                                        new Color(0.9882352941176471, 0.73333333333333328, 0.63137254901960782, 1.0),
                                        new Color(0.9882352941176471, 0.5725490196078431, 0.44705882352941179, 1.0),
                                        new Color(0.98431372549019602, 0.41568627450980394, 0.29019607843137257, 1.0),
                                        new Color(0.93725490196078431, 0.23137254901960785, 0.17254901960784313, 1.0),
                                        new Color(0.79607843137254897, 0.094117647058823528, 0.11372549019607843, 1.0),
                                        new Color(0.6470588235294118, 0.058823529411764705, 0.08235294117647058, 1.0),
                                        new Color(0.40392156862745099, 0.0, 0.05098039215686274, 1.0)));

        public static ColorMap Spectral = new ColorListColorMap("Spectral",
                        Arrays.asList(new Color(0.61960784313725492, 0.003921568627450980, 0.25882352941176473, 1.0),
                                        new Color(0.83529411764705885, 0.24313725490196078, 0.30980392156862746, 1.0),
                                        new Color(0.95686274509803926, 0.42745098039215684, 0.2627450980392157, 1.0),
                                        new Color(0.99215686274509807, 0.68235294117647061, 0.38039215686274508, 1.0),
                                        new Color(0.99607843137254903, 0.8784313725490196, 0.54509803921568623, 1.0),
                                        new Color(1.0, 1.0, 0.74901960784313726, 1.0),
                                        new Color(0.90196078431372551, 0.96078431372549022, 0.59607843137254901, 1.0),
                                        new Color(0.6705882352941176, 0.8666666666666667, 0.64313725490196083, 1.0),
                                        new Color(0.4, 0.76078431372549016, 0.6470588235294118, 1.0),
                                        new Color(0.19607843137254902, 0.53333333333333333, 0.74117647058823533, 1.0),
                                        new Color(0.36862745098039218, 0.30980392156862746, 0.63529411764705879, 1.0)));

        public static ColorMap YlGn = new ColorListColorMap("YlGn",
                        Arrays.asList(new Color(1.0, 1.0, 0.89803921568627454, 1.0),
                                        new Color(0.96862745098039216, 0.9882352941176471, 0.72549019607843135, 1.0),
                                        new Color(0.85098039215686272, 0.94117647058823528, 0.63921568627450975, 1.0),
                                        new Color(0.67843137254901964, 0.8666666666666667, 0.55686274509803924, 1.0),
                                        new Color(0.47058823529411764, 0.77647058823529413, 0.47450980392156861, 1.0),
                                        new Color(0.25490196078431371, 0.6705882352941176, 0.36470588235294116, 1.0),
                                        new Color(0.13725490196078433, 0.51764705882352946, 0.2627450980392157, 1.0),
                                        new Color(0.0, 0.40784313725490196, 0.21568627450980393, 1.0),
                                        new Color(0.0, 0.27058823529411763, 0.16078431372549021, 1.0)));

        public static ColorMap YlGnBu = new ColorListColorMap("YlGnBu",
                        Arrays.asList(new Color(1.0, 1.0, 0.85098039215686272, 1.0),
                                        new Color(0.92941176470588238, 0.97254901960784312, 0.69411764705882351, 1.0),
                                        new Color(0.7803921568627451, 0.9137254901960784, 0.70588235294117652, 1.0),
                                        new Color(0.49803921568627452, 0.80392156862745101, 0.73333333333333328, 1.0),
                                        new Color(0.25490196078431371, 0.71372549019607845, 0.7686274509803922, 1.0),
                                        new Color(0.11372549019607843, 0.56862745098039214, 0.75294117647058822, 1.0),
                                        new Color(0.13333333333333333, 0.36862745098039218, 0.6588235294117647, 1.0),
                                        new Color(0.14509803921568629, 0.20392156862745098, 0.58039215686274515, 1.0),
                                        new Color(0.03137254901960784, 0.11372549019607843, 0.34509803921568627, 1.0)));

        public static ColorMap YlOrBr = new ColorListColorMap("YlOrBr",
                        Arrays.asList(new Color(1.0, 1.0, 0.89803921568627454, 1.0),
                                        new Color(1.0, 0.96862745098039216, 0.73725490196078436, 1.0),
                                        new Color(0.99607843137254903, 0.8901960784313725, 0.56862745098039214, 1.0),
                                        new Color(0.99607843137254903, 0.7686274509803922, 0.30980392156862746, 1.0),
                                        new Color(0.99607843137254903, 0.6, 0.16078431372549021, 1.0),
                                        new Color(0.92549019607843142, 0.4392156862745098, 0.07843137254901961, 1.0),
                                        new Color(0.8, 0.29803921568627451, 0.00784313725490196, 1.0),
                                        new Color(0.6, 0.20392156862745098, 0.01568627450980392, 1.0),
                                        new Color(0.4, 0.14509803921568629, 0.02352941176470588, 1.0)));

        public static ColorMap YlOrRd = new ColorListColorMap("YlOrRd",
                        Arrays.asList(new Color(1.0, 1.0, 0.8, 1.0),
                                        new Color(1.0, 0.92941176470588238, 0.62745098039215685, 1.0),
                                        new Color(0.99607843137254903, 0.85098039215686272, 0.46274509803921571, 1.0),
                                        new Color(0.99607843137254903, 0.69803921568627447, 0.29803921568627451, 1.0),
                                        new Color(0.99215686274509807, 0.55294117647058827, 0.23529411764705882, 1.0),
                                        new Color(0.9882352941176471, 0.30588235294117649, 0.16470588235294117, 1.0),
                                        new Color(0.8901960784313725, 0.10196078431372549, 0.10980392156862745, 1.0),
                                        new Color(0.74117647058823533, 0.0, 0.14901960784313725, 1.0),
                                        new Color(0.50196078431372548, 0.0, 0.14901960784313725, 1.0)));

        /**
         * ColorBrewer's qualitative maps, implemented using ListedColormap for use with
         * mpl.colors.NoNorm
         */

        public static ColorMap Accent = new ColorListColorMap("Accent",
                        Arrays.asList(new Color(0.49803921568627452, 0.78823529411764703, 0.49803921568627452, 1.0),
                                        new Color(0.74509803921568629, 0.68235294117647061, 0.83137254901960789, 1.0),
                                        new Color(0.99215686274509807, 0.75294117647058822, 0.52549019607843139, 1.0),
                                        new Color(1.0, 1.0, 0.6, 1.0),
                                        new Color(0.2196078431372549, 0.42352941176470588, 0.69019607843137254, 1.0),
                                        new Color(0.94117647058823528, 0.00784313725490196, 0.49803921568627452, 1.0),
                                        new Color(0.74901960784313726, 0.35686274509803922, 0.09019607843137254, 1.0),
                                        new Color(0.4, 0.4, 0.4, 1.0)));

        public static ColorMap Dark2 = new ColorListColorMap("Dark2",
                        Arrays.asList(new Color(0.10588235294117647, 0.61960784313725492, 0.46666666666666667, 1.0),
                                        new Color(0.85098039215686272, 0.37254901960784315, 0.00784313725490196, 1.0),
                                        new Color(0.45882352941176469, 0.4392156862745098, 0.70196078431372544, 1.0),
                                        new Color(0.90588235294117647, 0.16078431372549021, 0.54117647058823526, 1.0),
                                        new Color(0.4, 0.65098039215686276, 0.11764705882352941, 1.0),
                                        new Color(0.90196078431372551, 0.6705882352941176, 0.00784313725490196, 1.0),
                                        new Color(0.65098039215686276, 0.46274509803921571, 0.11372549019607843, 1.0),
                                        new Color(0.4, 0.4, 0.4, 1.0)));

        public static ColorMap Paired = new ColorListColorMap("Paired",
                        Arrays.asList(new Color(0.65098039215686276, 0.80784313725490198, 0.8901960784313725, 1.0),
                                        new Color(0.12156862745098039, 0.47058823529411764, 0.70588235294117652, 1.0),
                                        new Color(0.69803921568627447, 0.87450980392156863, 0.54117647058823526, 1.0),
                                        new Color(0.2, 0.62745098039215685, 0.17254901960784313, 1.0),
                                        new Color(0.98431372549019602, 0.60392156862745094, 0.6, 1.0),
                                        new Color(0.8901960784313725, 0.10196078431372549, 0.10980392156862745, 1.0),
                                        new Color(0.99215686274509807, 0.74901960784313726, 0.43529411764705883, 1.0),
                                        new Color(1.0, 0.49803921568627452, 0.0, 1.0),
                                        new Color(0.792156862745098, 0.69803921568627447, 0.83921568627450982, 1.0),
                                        new Color(0.41568627450980394, 0.23921568627450981, 0.60392156862745094, 1.0),
                                        new Color(1.0, 1.0, 0.6, 1.0),
                                        new Color(0.69411764705882351, 0.34901960784313724, 0.15686274509803921, 1.0)));

        public static ColorMap Pastel1 = new ColorListColorMap("Pastel1",
                        Arrays.asList(new Color(0.98431372549019602, 0.70588235294117652, 0.68235294117647061, 1.0),
                                        new Color(0.70196078431372544, 0.80392156862745101, 0.8901960784313725, 1.0),
                                        new Color(0.8, 0.92156862745098034, 0.77254901960784317, 1.0),
                                        new Color(0.87058823529411766, 0.79607843137254897, 0.89411764705882357, 1.0),
                                        new Color(0.99607843137254903, 0.85098039215686272, 0.65098039215686276, 1.0),
                                        new Color(1.0, 1.0, 0.8, 1.0),
                                        new Color(0.89803921568627454, 0.84705882352941175, 0.74117647058823533, 1.0),
                                        new Color(0.99215686274509807, 0.85490196078431369, 0.92549019607843142, 1.0),
                                        new Color(0.94901960784313721, 0.94901960784313721, 0.94901960784313721, 1.0)));

        public static ColorMap Pastel2 = new ColorListColorMap("Pastel2",
                        Arrays.asList(new Color(0.70196078431372544, 0.88627450980392153, 0.80392156862745101, 1.0),
                                        new Color(0.99215686274509807, 0.80392156862745101, 0.67450980392156867, 1.0),
                                        new Color(0.79607843137254897, 0.83529411764705885, 0.90980392156862744, 1.0),
                                        new Color(0.95686274509803926, 0.792156862745098, 0.89411764705882357, 1.0),
                                        new Color(0.90196078431372551, 0.96078431372549022, 0.78823529411764703, 1.0),
                                        new Color(1.0, 0.94901960784313721, 0.68235294117647061, 1.0),
                                        new Color(0.94509803921568625, 0.88627450980392153, 0.8, 1.0),
                                        new Color(0.8, 0.8, 0.8, 1.0)));

        public static ColorMap Set1 = new ColorListColorMap("Set1",
                        Arrays.asList(new Color(0.89411764705882357, 0.10196078431372549, 0.10980392156862745, 1.0),
                                        new Color(0.21568627450980393, 0.49411764705882355, 0.72156862745098038, 1.0),
                                        new Color(0.30196078431372547, 0.68627450980392157, 0.29019607843137257, 1.0),
                                        new Color(0.59607843137254901, 0.30588235294117649, 0.63921568627450975, 1.0),
                                        new Color(1.0, 0.49803921568627452, 0.0, 1.0), new Color(1.0, 1.0, 0.2, 1.0),
                                        new Color(0.65098039215686276, 0.33725490196078434, 0.15686274509803921, 1.0),
                                        new Color(0.96862745098039216, 0.50588235294117645, 0.74901960784313726, 1.0),
                                        new Color(0.6, 0.6, 0.6, 1.0)));

        public static ColorMap Set2 = new ColorListColorMap("Set2",
                        Arrays.asList(new Color(0.4, 0.76078431372549016, 0.6470588235294118, 1.0),
                                        new Color(0.9882352941176471, 0.55294117647058827, 0.3843137254901961, 1.0),
                                        new Color(0.55294117647058827, 0.62745098039215685, 0.79607843137254897, 1.0),
                                        new Color(0.90588235294117647, 0.54117647058823526, 0.76470588235294112, 1.0),
                                        new Color(0.65098039215686276, 0.84705882352941175, 0.32941176470588235, 1.0),
                                        new Color(1.0, 0.85098039215686272, 0.18431372549019609, 1.0),
                                        new Color(0.89803921568627454, 0.7686274509803922, 0.58039215686274515, 1.0),
                                        new Color(0.70196078431372544, 0.70196078431372544, 0.70196078431372544, 1.0)));

        public static ColorMap Set3 = new ColorListColorMap("Set3",
                        Arrays.asList(new Color(0.55294117647058827, 0.82745098039215681, 0.7803921568627451, 1.0),
                                        new Color(1.0, 1.0, 0.70196078431372544, 1.0),
                                        new Color(0.74509803921568629, 0.72941176470588232, 0.85490196078431369, 1.0),
                                        new Color(0.98431372549019602, 0.50196078431372548, 0.44705882352941179, 1.0),
                                        new Color(0.50196078431372548, 0.69411764705882351, 0.82745098039215681, 1.0),
                                        new Color(0.99215686274509807, 0.70588235294117652, 0.3843137254901961, 1.0),
                                        new Color(0.70196078431372544, 0.87058823529411766, 0.41176470588235292, 1.0),
                                        new Color(0.9882352941176471, 0.80392156862745101, 0.89803921568627454, 1.0),
                                        new Color(0.85098039215686272, 0.85098039215686272, 0.85098039215686272, 1.0),
                                        new Color(0.73725490196078436, 0.50196078431372548, 0.74117647058823533, 1.0),
                                        new Color(0.8, 0.92156862745098034, 0.77254901960784317, 1.0),
                                        new Color(1.0, 0.92941176470588238, 0.43529411764705883, 1.0)));

        /*
         * The next 7 palettes are from the Yorick scientific visualization package, an
         * evolution of the GIST package, both by David H. Munro.
         * 
         * They are released under a BSD-like license( see LICENSE_YORICK in#the license
         * directory of the matplotlib source distribution).
         * 
         * Most palette functions have been reduced to simple function descriptions by
         * Reinier Heeres,since the rgb components were mostly straight lines.
         * 
         * gist_earth_data and gist_ncar_data were simplified by a script and somemanual
         * effort.
         */
        public static ColorMap gist_earth = new SeperateRGBListColorMap("gist_earth", Arrays.asList(
                        new SinglePoint(0.0, 0.0, 0.0000), new SinglePoint(0.2824, 0.1882, 0.1882),
                        new SinglePoint(0.4588, 0.2714, 0.2714), new SinglePoint(0.5490, 0.4719, 0.4719),
                        new SinglePoint(0.6980, 0.7176, 0.7176), new SinglePoint(0.7882, 0.7553, 0.7553),
                        new SinglePoint(1.0000, 0.9922, 0.9922)),
                        Arrays.asList(new SinglePoint(0.0, 0.0, 0.0000), new SinglePoint(0.0275, 0.0000, 0.0000),
                                        new SinglePoint(0.1098, 0.1893, 0.1893),
                                        new SinglePoint(0.1647, 0.3035, 0.3035),
                                        new SinglePoint(0.2078, 0.3841, 0.3841),
                                        new SinglePoint(0.2824, 0.5020, 0.5020),
                                        new SinglePoint(0.5216, 0.6397, 0.6397),
                                        new SinglePoint(0.6980, 0.7171, 0.7171),
                                        new SinglePoint(0.7882, 0.6392, 0.6392),
                                        new SinglePoint(0.7922, 0.6413, 0.6413),
                                        new SinglePoint(0.8000, 0.6447, 0.6447),
                                        new SinglePoint(0.8078, 0.6481, 0.6481),
                                        new SinglePoint(0.8157, 0.6549, 0.6549),
                                        new SinglePoint(0.8667, 0.6991, 0.6991),
                                        new SinglePoint(0.8745, 0.7103, 0.7103),
                                        new SinglePoint(0.8824, 0.7216, 0.7216),
                                        new SinglePoint(0.8902, 0.7323, 0.7323),
                                        new SinglePoint(0.8980, 0.7430, 0.7430),
                                        new SinglePoint(0.9412, 0.8275, 0.8275),
                                        new SinglePoint(0.9569, 0.8635, 0.8635),
                                        new SinglePoint(0.9647, 0.8816, 0.8816),
                                        new SinglePoint(0.9961, 0.9733, 0.9733),
                                        new SinglePoint(1.0000, 0.9843, 0.9843)),
                        Arrays.asList(new SinglePoint(0.0, 0.0, 0.0000), new SinglePoint(0.0039, 0.1684, 0.1684),
                                        new SinglePoint(0.0078, 0.2212, 0.2212),
                                        new SinglePoint(0.0275, 0.4329, 0.4329),
                                        new SinglePoint(0.0314, 0.4549, 0.4549),
                                        new SinglePoint(0.2824, 0.5004, 0.5004),
                                        new SinglePoint(0.4667, 0.2748, 0.2748),
                                        new SinglePoint(0.5451, 0.3205, 0.3205),
                                        new SinglePoint(0.7843, 0.3961, 0.3961),
                                        new SinglePoint(0.8941, 0.6651, 0.6651),
                                        new SinglePoint(1.0000, 0.9843, 0.9843)));

        public static ColorMap gist_gray = new FunctionColorMap("gist_gray", ColorMaps::g3, ColorMaps::g3,
                        ColorMaps::g3);

        private static double gist_heat_red(double x) {
                return 1.5 * x;
        }

        private static double gist_heat_green(double x) {
                return 2 * x - 1;
        }

        private static double gist_heat_blue(double x) {
                return 4 * x - 3;
        }

        public static ColorMap gist_heat = new FunctionColorMap("gist_heat", ColorMaps::gist_heat_red,
                        ColorMaps::gist_heat_green, ColorMaps::gist_heat_blue);

        public static ColorMap gist_ncar = new SeperateRGBListColorMap("gist_ncar", Arrays.asList(
                        new SinglePoint(0.0, 0.0, 0.0000), new SinglePoint(0.3098, 0.0000, 0.0000),
                        new SinglePoint(0.3725, 0.3993, 0.3993), new SinglePoint(0.4235, 0.5003, 0.5003),
                        new SinglePoint(0.5333, 1.0000, 1.0000), new SinglePoint(0.7922, 1.0000, 1.0000),
                        new SinglePoint(0.8471, 0.6218, 0.6218), new SinglePoint(0.8980, 0.9235, 0.9235),
                        new SinglePoint(1.0000, 0.9961, 0.9961)),
                        Arrays.asList(new SinglePoint(0.0, 0.0, 0.0000), new SinglePoint(0.0510, 0.3722, 0.3722),
                                        new SinglePoint(0.1059, 0.0000, 0.0000),
                                        new SinglePoint(0.1569, 0.7202, 0.7202),
                                        new SinglePoint(0.1608, 0.7537, 0.7537),
                                        new SinglePoint(0.1647, 0.7752, 0.7752),
                                        new SinglePoint(0.2157, 1.0000, 1.0000),
                                        new SinglePoint(0.2588, 0.9804, 0.9804),
                                        new SinglePoint(0.2706, 0.9804, 0.9804),
                                        new SinglePoint(0.3176, 1.0000, 1.0000),
                                        new SinglePoint(0.3686, 0.8081, 0.8081),
                                        new SinglePoint(0.4275, 1.0000, 1.0000),
                                        new SinglePoint(0.5216, 1.0000, 1.0000),
                                        new SinglePoint(0.6314, 0.7292, 0.7292),
                                        new SinglePoint(0.6863, 0.2796, 0.2796),
                                        new SinglePoint(0.7451, 0.0000, 0.0000),
                                        new SinglePoint(0.7922, 0.0000, 0.0000),
                                        new SinglePoint(0.8431, 0.1753, 0.1753),
                                        new SinglePoint(0.8980, 0.5000, 0.5000),
                                        new SinglePoint(1.0000, 0.9725, 0.9725)),
                        Arrays.asList(new SinglePoint(0.0, 0.5020, 0.5020), new SinglePoint(0.0510, 0.0222, 0.0222),
                                        new SinglePoint(0.1098, 1.0000, 1.0000),
                                        new SinglePoint(0.2039, 1.0000, 1.0000),
                                        new SinglePoint(0.2627, 0.6145, 0.6145),
                                        new SinglePoint(0.3216, 0.0000, 0.0000),
                                        new SinglePoint(0.4157, 0.0000, 0.0000),
                                        new SinglePoint(0.4745, 0.2342, 0.2342),
                                        new SinglePoint(0.5333, 0.0000, 0.0000),
                                        new SinglePoint(0.5804, 0.0000, 0.0000),
                                        new SinglePoint(0.6314, 0.0549, 0.0549),
                                        new SinglePoint(0.6902, 0.0000, 0.0000),
                                        new SinglePoint(0.7373, 0.0000, 0.0000),
                                        new SinglePoint(0.7922, 0.9738, 0.9738),
                                        new SinglePoint(0.8000, 1.0000, 1.0000),
                                        new SinglePoint(0.8431, 1.0000, 1.0000),
                                        new SinglePoint(0.8980, 0.9341, 0.9341),
                                        new SinglePoint(1.0000, 0.9961, 0.9961)));

        public static ColorMap gist_rainbow = new RGBPointListColorMap("gist_rainbow",
                        Arrays.asList(new RGBPoint(0.000, new Color(1.00, 0.00, 0.16, 1.0)),
                                        new RGBPoint(0.030, new Color(1.00, 0.00, 0.00, 1.0)),
                                        new RGBPoint(0.215, new Color(1.00, 1.00, 0.00, 1.0)),
                                        new RGBPoint(0.400, new Color(0.00, 1.00, 0.00, 1.0)),
                                        new RGBPoint(0.586, new Color(0.00, 1.00, 1.00, 1.0)),
                                        new RGBPoint(0.770, new Color(0.00, 0.00, 1.00, 1.0)),
                                        new RGBPoint(0.954, new Color(1.00, 0.00, 1.00, 1.0)),
                                        new RGBPoint(1.000, new Color(1.00, 0.00, 0.75, 1.0))));

        public static ColorMap gist_stern = new SeperateRGBListColorMap("gist_stern",
                        Arrays.asList(new SinglePoint(0.000, 0.000, 0.000), new SinglePoint(0.0547, 1.000, 1.000),
                                        new SinglePoint(0.250, 0.027, 0.250), new SinglePoint(1.000, 1.000, 1.000)),
                        Arrays.asList(new SinglePoint(0, 0, 0), new SinglePoint(1, 1, 1)),
                        Arrays.asList(new SinglePoint(0.000, 0.000, 0.000), new SinglePoint(0.500, 1.000, 1.000),
                                        new SinglePoint(0.735, 0.000, 0.000), new SinglePoint(1.000, 1.000, 1.000)));

        private static double gist_yarg(double x) {
                return 1 - x;
        }

        public static ColorMap gist_yarg = new FunctionColorMap("gist_yarg", ColorMaps::gist_yarg, ColorMaps::gist_yarg,
                        ColorMaps::gist_yarg);

        /*
         * This bipolar colormap was generated from CoolWarmFloat33.csv of "Diverging
         * Color Maps for Scientific Visualization" by Kenneth Moreland.
         * <http://www.kennethmoreland.com/color-maps/>
         */
        public static ColorMap coolwarm = new SeperateRGBListColorMap("coolwarm",
                        Arrays.asList(new SinglePoint(0.0, 0.2298057, 0.2298057),
                                        new SinglePoint(0.03125, 0.26623388, 0.26623388),
                                        new SinglePoint(0.0625, 0.30386891, 0.30386891),
                                        new SinglePoint(0.09375, 0.342804478, 0.342804478),
                                        new SinglePoint(0.125, 0.38301334, 0.38301334),
                                        new SinglePoint(0.15625, 0.424369608, 0.424369608),
                                        new SinglePoint(0.1875, 0.46666708, 0.46666708),
                                        new SinglePoint(0.21875, 0.509635204, 0.509635204),
                                        new SinglePoint(0.25, 0.552953156, 0.552953156),
                                        new SinglePoint(0.28125, 0.596262162, 0.596262162),
                                        new SinglePoint(0.3125, 0.639176211, 0.639176211),
                                        new SinglePoint(0.34375, 0.681291281, 0.681291281),
                                        new SinglePoint(0.375, 0.722193294, 0.722193294),
                                        new SinglePoint(0.40625, 0.761464949, 0.761464949),
                                        new SinglePoint(0.4375, 0.798691636, 0.798691636),
                                        new SinglePoint(0.46875, 0.833466556, 0.833466556),
                                        new SinglePoint(0.5, 0.865395197, 0.865395197),
                                        new SinglePoint(0.53125, 0.897787179, 0.897787179),
                                        new SinglePoint(0.5625, 0.924127593, 0.924127593),
                                        new SinglePoint(0.59375, 0.944468518, 0.944468518),
                                        new SinglePoint(0.625, 0.958852946, 0.958852946),
                                        new SinglePoint(0.65625, 0.96732803, 0.96732803),
                                        new SinglePoint(0.6875, 0.969954137, 0.969954137),
                                        new SinglePoint(0.71875, 0.966811177, 0.966811177),
                                        new SinglePoint(0.75, 0.958003065, 0.958003065),
                                        new SinglePoint(0.78125, 0.943660866, 0.943660866),
                                        new SinglePoint(0.8125, 0.923944917, 0.923944917),
                                        new SinglePoint(0.84375, 0.89904617, 0.89904617),
                                        new SinglePoint(0.875, 0.869186849, 0.869186849),
                                        new SinglePoint(0.90625, 0.834620542, 0.834620542),
                                        new SinglePoint(0.9375, 0.795631745, 0.795631745),
                                        new SinglePoint(0.96875, 0.752534934, 0.752534934),
                                        new SinglePoint(1.0, 0.705673158, 0.705673158)),
                        Arrays.asList(new SinglePoint(0.0, 0.298717966, 0.298717966),
                                        new SinglePoint(0.03125, 0.353094838, 0.353094838),
                                        new SinglePoint(0.0625, 0.406535296, 0.406535296),
                                        new SinglePoint(0.09375, 0.458757618, 0.458757618),
                                        new SinglePoint(0.125, 0.50941904, 0.50941904),
                                        new SinglePoint(0.15625, 0.558148092, 0.558148092),
                                        new SinglePoint(0.1875, 0.604562568, 0.604562568),
                                        new SinglePoint(0.21875, 0.648280772, 0.648280772),
                                        new SinglePoint(0.25, 0.688929332, 0.688929332),
                                        new SinglePoint(0.28125, 0.726149107, 0.726149107),
                                        new SinglePoint(0.3125, 0.759599947, 0.759599947),
                                        new SinglePoint(0.34375, 0.788964712, 0.788964712),
                                        new SinglePoint(0.375, 0.813952739, 0.813952739),
                                        new SinglePoint(0.40625, 0.834302879, 0.834302879),
                                        new SinglePoint(0.4375, 0.849786142, 0.849786142),
                                        new SinglePoint(0.46875, 0.860207984, 0.860207984),
                                        new SinglePoint(0.5, 0.86541021, 0.86541021),
                                        new SinglePoint(0.53125, 0.848937047, 0.848937047),
                                        new SinglePoint(0.5625, 0.827384882, 0.827384882),
                                        new SinglePoint(0.59375, 0.800927443, 0.800927443),
                                        new SinglePoint(0.625, 0.769767752, 0.769767752),
                                        new SinglePoint(0.65625, 0.734132809, 0.734132809),
                                        new SinglePoint(0.6875, 0.694266682, 0.694266682),
                                        new SinglePoint(0.71875, 0.650421156, 0.650421156),
                                        new SinglePoint(0.75, 0.602842431, 0.602842431),
                                        new SinglePoint(0.78125, 0.551750968, 0.551750968),
                                        new SinglePoint(0.8125, 0.49730856, 0.49730856),
                                        new SinglePoint(0.84375, 0.439559467, 0.439559467),
                                        new SinglePoint(0.875, 0.378313092, 0.378313092),
                                        new SinglePoint(0.90625, 0.312874446, 0.312874446),
                                        new SinglePoint(0.9375, 0.24128379, 0.24128379),
                                        new SinglePoint(0.96875, 0.157246067, 0.157246067),
                                        new SinglePoint(1.0, 0.01555616, 0.01555616)),
                        Arrays.asList(new SinglePoint(0.0, 0.753683153, 0.753683153),
                                        new SinglePoint(0.03125, 0.801466763, 0.801466763),
                                        new SinglePoint(0.0625, 0.84495867, 0.84495867),
                                        new SinglePoint(0.09375, 0.883725899, 0.883725899),
                                        new SinglePoint(0.125, 0.917387822, 0.917387822),
                                        new SinglePoint(0.15625, 0.945619588, 0.945619588),
                                        new SinglePoint(0.1875, 0.968154911, 0.968154911),
                                        new SinglePoint(0.21875, 0.98478814, 0.98478814),
                                        new SinglePoint(0.25, 0.995375608, 0.995375608),
                                        new SinglePoint(0.28125, 0.999836203, 0.999836203),
                                        new SinglePoint(0.3125, 0.998151185, 0.998151185),
                                        new SinglePoint(0.34375, 0.990363227, 0.990363227),
                                        new SinglePoint(0.375, 0.976574709, 0.976574709),
                                        new SinglePoint(0.40625, 0.956945269, 0.956945269),
                                        new SinglePoint(0.4375, 0.931688648, 0.931688648),
                                        new SinglePoint(0.46875, 0.901068838, 0.901068838),
                                        new SinglePoint(0.5, 0.865395561, 0.865395561),
                                        new SinglePoint(0.53125, 0.820880546, 0.820880546),
                                        new SinglePoint(0.5625, 0.774508472, 0.774508472),
                                        new SinglePoint(0.59375, 0.726736146, 0.726736146),
                                        new SinglePoint(0.625, 0.678007945, 0.678007945),
                                        new SinglePoint(0.65625, 0.628751763, 0.628751763),
                                        new SinglePoint(0.6875, 0.579375448, 0.579375448),
                                        new SinglePoint(0.71875, 0.530263762, 0.530263762),
                                        new SinglePoint(0.75, 0.481775914, 0.481775914),
                                        new SinglePoint(0.78125, 0.434243684, 0.434243684),
                                        new SinglePoint(0.8125, 0.387970225, 0.387970225),
                                        new SinglePoint(0.84375, 0.343229596, 0.343229596),
                                        new SinglePoint(0.875, 0.300267182, 0.300267182),
                                        new SinglePoint(0.90625, 0.259301199, 0.259301199),
                                        new SinglePoint(0.9375, 0.220525627, 0.220525627),
                                        new SinglePoint(0.96875, 0.184115123, 0.184115123),
                                        new SinglePoint(1.0, 0.150232812, 0.150232812)));

        /*
         * Implementation of Carey Rappaport's CMRmap. See `A Color Map for Effective
         * Black-and-White Rendering of Color-Scale Images' by Carey Rappaport
         * http://www.mathworks.com/matlabcentral/fileexchange/2662-cmrmap-m
         */
        public static ColorMap CMRmap = new SeperateRGBListColorMap("CMRmap",
                        Arrays.asList(new SinglePoint(0.000, 0.00, 0.00), new SinglePoint(0.125, 0.15, 0.15),
                                        new SinglePoint(0.250, 0.30, 0.30), new SinglePoint(0.375, 0.60, 0.60),
                                        new SinglePoint(0.500, 1.00, 1.00), new SinglePoint(0.625, 0.90, 0.90),
                                        new SinglePoint(0.750, 0.90, 0.90), new SinglePoint(0.875, 0.90, 0.90),
                                        new SinglePoint(1.000, 1.00, 1.00)),
                        Arrays.asList(new SinglePoint(0.000, 0.00, 0.00), new SinglePoint(0.125, 0.15, 0.15),
                                        new SinglePoint(0.250, 0.15, 0.15), new SinglePoint(0.375, 0.20, 0.20),
                                        new SinglePoint(0.500, 0.25, 0.25), new SinglePoint(0.625, 0.50, 0.50),
                                        new SinglePoint(0.750, 0.75, 0.75), new SinglePoint(0.875, 0.90, 0.90),
                                        new SinglePoint(1.000, 1.00, 1.00)),
                        Arrays.asList(new SinglePoint(0.000, 0.00, 0.00), new SinglePoint(0.125, 0.50, 0.50),
                                        new SinglePoint(0.250, 0.75, 0.75), new SinglePoint(0.375, 0.50, 0.50),
                                        new SinglePoint(0.500, 0.15, 0.15), new SinglePoint(0.625, 0.00, 0.00),
                                        new SinglePoint(0.750, 0.10, 0.10), new SinglePoint(0.875, 0.50, 0.50),
                                        new SinglePoint(1.000, 1.00, 1.00)));
        /*
         * An MIT licensed, colorblind-friendly heatmap from Wistia:
         * https://github.com/wistia/heatmap-palette
         * http://wistia.com/blog/heatmaps-for-colorblindness
         */
        public static ColorMap wistia = new SeperateRGBListColorMap("wistia",
                        Arrays.asList(new SinglePoint(0.0, 0.8941176470588236, 0.8941176470588236),
                                        new SinglePoint(0.25, 1.0, 1.0), new SinglePoint(0.5, 1.0, 1.0),
                                        new SinglePoint(0.75, 1.0, 1.0),
                                        new SinglePoint(1.0, 0.9882352941176471, 0.9882352941176471)),
                        Arrays.asList(new SinglePoint(0.0, 1.0, 1.0),
                                        new SinglePoint(0.25, 0.9098039215686274, 0.9098039215686274),
                                        new SinglePoint(0.5, 0.7411764705882353, 0.7411764705882353),
                                        new SinglePoint(0.75, 0.6274509803921569, 0.6274509803921569),
                                        new SinglePoint(1.0, 0.4980392156862745, 0.4980392156862745)),
                        Arrays.asList(new SinglePoint(0.0, 0.47843137254901963, 0.47843137254901963),
                                        new SinglePoint(0.25, 0.10196078431372549, 0.10196078431372549),
                                        new SinglePoint(0.5, 0.0, 0.0), new SinglePoint(0.75, 0.0, 0.0),
                                        new SinglePoint(1.0, 0.0, 0.0)));

        /*
         * Categorical palettes from Vega: https://github.com/vega/vega/wiki/Scales
         * (divided by 255)
         */

        public static ColorMap tab10 = new ColorListColorMap("tab10",
                        Arrays.asList(new Color(0.12156862745098039, 0.4666666666666667, 0.7058823529411765, 1.0), // 1f77b4
                                        new Color(1.0, 0.4980392156862745, 0.054901960784313725, 1.0), // ff7f0e
                                        new Color(0.17254901960784313, 0.6274509803921569, 0.17254901960784313, 1.0), // 2ca02c
                                        new Color(0.8392156862745098, 0.15294117647058825, 0.1568627450980392, 1.0), // d62728
                                        new Color(0.5803921568627451, 0.403921568627451, 0.7411764705882353, 1.0), // 9467bd
                                        new Color(0.5490196078431373, 0.33725490196078434, 0.29411764705882354, 1.0), // 8c564b
                                        new Color(0.8901960784313725, 0.4666666666666667, 0.7607843137254902, 1.0), // e377c2
                                        new Color(0.4980392156862745, 0.4980392156862745, 0.4980392156862745, 1.0), // 7f7f7f
                                        new Color(0.7372549019607844, 0.7411764705882353, 0.13333333333333333, 1.0), // bcbd22
                                        new Color(0.09019607843137255, 0.7450980392156863, 0.8117647058823529, 1.0) // 17becf
                        ));

        public static ColorMap tab20 = new ColorListColorMap("tab20",
                        Arrays.asList(new Color(0.12156862745098039, 0.4666666666666667, 0.7058823529411765, 1.0), // 1f77b4
                                        new Color(0.6823529411764706, 0.7803921568627451, 0.9098039215686274, 1.0), // aec7e8
                                        new Color(1.0, 0.4980392156862745, 0.054901960784313725, 1.0), // ff7f0e
                                        new Color(1.0, 0.7333333333333333, 0.47058823529411764, 1.0), // ffbb78
                                        new Color(0.17254901960784313, 0.6274509803921569, 0.17254901960784313, 1.0), // 2ca02c
                                        new Color(0.596078431372549, 0.8745098039215686, 0.5411764705882353, 1.0), // 98df8a
                                        new Color(0.8392156862745098, 0.15294117647058825, 0.1568627450980392, 1.0), // d62728
                                        new Color(1.0, 0.596078431372549, 0.5882352941176471, 1.0), // ff9896
                                        new Color(0.5803921568627451, 0.403921568627451, 0.7411764705882353, 1.0), // 9467bd
                                        new Color(0.7725490196078432, 0.6901960784313725, 0.8352941176470589, 1.0), // c5b0d5
                                        new Color(0.5490196078431373, 0.33725490196078434, 0.29411764705882354, 1.0), // 8c564b
                                        new Color(0.7686274509803922, 0.611764705882353, 0.5803921568627451, 1.0), // c49c94
                                        new Color(0.8901960784313725, 0.4666666666666667, 0.7607843137254902, 1.0), // e377c2
                                        new Color(0.9686274509803922, 0.7137254901960784, 0.8235294117647058, 1.0), // f7b6d2
                                        new Color(0.4980392156862745, 0.4980392156862745, 0.4980392156862745, 1.0), // 7f7f7f
                                        new Color(0.7803921568627451, 0.7803921568627451, 0.7803921568627451, 1.0), // c7c7c7
                                        new Color(0.7372549019607844, 0.7411764705882353, 0.13333333333333333, 1.0), // bcbd22
                                        new Color(0.8588235294117647, 0.8588235294117647, 0.5529411764705883, 1.0), // dbdb8d
                                        new Color(0.09019607843137255, 0.7450980392156863, 0.8117647058823529, 1.0), // 17becf
                                        new Color(0.6196078431372549, 0.8549019607843137, 0.8980392156862745, 1.0) // 9edae5
                        ));

        public static ColorMap tab20b = new ColorListColorMap("tab20b",
                        Arrays.asList(new Color(0.2235294117647059, 0.23137254901960785, 0.4745098039215686, 1.0), // 393b79
                                        new Color(0.3215686274509804, 0.32941176470588235, 0.6392156862745098, 1.0), // 5254a3
                                        new Color(0.4196078431372549, 0.43137254901960786, 0.8117647058823529, 1.0), // 6b6ecf
                                        new Color(0.611764705882353, 0.6196078431372549, 0.8705882352941177, 1.0), // 9c9ede
                                        new Color(0.38823529411764707, 0.4745098039215686, 0.2235294117647059, 1.0), // 637939
                                        new Color(0.5490196078431373, 0.6352941176470588, 0.3215686274509804, 1.0), // 8ca252
                                        new Color(0.7098039215686275, 0.8117647058823529, 0.4196078431372549, 1.0), // b5cf6b
                                        new Color(0.807843137254902, 0.8588235294117647, 0.611764705882353, 1.0), // cedb9c
                                        new Color(0.5490196078431373, 0.42745098039215684, 0.19215686274509805, 1.0), // 8c6d31
                                        new Color(0.7411764705882353, 0.6196078431372549, 0.2235294117647059, 1.0), // bd9e39
                                        new Color(0.9058823529411765, 0.7294117647058823, 0.3215686274509804, 1.0), // e7ba52
                                        new Color(0.9058823529411765, 0.796078431372549, 0.5803921568627451, 1.0), // e7cb94
                                        new Color(0.5176470588235295, 0.23529411764705882, 0.2235294117647059, 1.0), // 843c39
                                        new Color(0.6784313725490196, 0.28627450980392155, 0.2901960784313726, 1.0), // ad494a
                                        new Color(0.8392156862745098, 0.3803921568627451, 0.4196078431372549, 1.0), // d6616b
                                        new Color(0.9058823529411765, 0.5882352941176471, 0.611764705882353, 1.0), // e7969c
                                        new Color(0.4823529411764706, 0.2549019607843137, 0.45098039215686275, 1.0), // 7b4173
                                        new Color(0.6470588235294118, 0.3176470588235294, 0.5803921568627451, 1.0), // a55194
                                        new Color(0.807843137254902, 0.42745098039215684, 0.7411764705882353, 1.0), // ce6dbd
                                        new Color(0.8705882352941177, 0.6196078431372549, 0.8392156862745098, 1.0) // de9ed6
                        ));

        public static ColorMap tab20c = new ColorListColorMap("tab20c",
                        Arrays.asList(new Color(0.19215686274509805, 0.5098039215686274, 0.7411764705882353, 1.0), // 3182bd
                                        new Color(0.4196078431372549, 0.6823529411764706, 0.8392156862745098, 1.0), // 6baed6
                                        new Color(0.6196078431372549, 0.792156862745098, 0.8823529411764706, 1.0), // 9ecae1
                                        new Color(0.7764705882352941, 0.8588235294117647, 0.9372549019607843, 1.0), // c6dbef
                                        new Color(0.9019607843137255, 0.3333333333333333, 0.050980392156862744, 1.0), // e6550d
                                        new Color(0.9921568627450981, 0.5529411764705883, 0.23529411764705882, 1.0), // fd8d3c
                                        new Color(0.9921568627450981, 0.6823529411764706, 0.4196078431372549, 1.0), // fdae6b
                                        new Color(0.9921568627450981, 0.8156862745098039, 0.6352941176470588, 1.0), // fdd0a2
                                        new Color(0.19215686274509805, 0.6392156862745098, 0.32941176470588235, 1.0), // 31a354
                                        new Color(0.4549019607843137, 0.7686274509803922, 0.4627450980392157, 1.0), // 74c476
                                        new Color(0.6313725490196078, 0.8509803921568627, 0.6078431372549019, 1.0), // a1d99b
                                        new Color(0.7803921568627451, 0.9137254901960784, 0.7529411764705882, 1.0), // c7e9c0
                                        new Color(0.4588235294117647, 0.4196078431372549, 0.6941176470588235, 1.0), // 756bb1
                                        new Color(0.6196078431372549, 0.6039215686274509, 0.7843137254901961, 1.0), // 9e9ac8
                                        new Color(0.7372549019607844, 0.7411764705882353, 0.8627450980392157, 1.0), // bcbddc
                                        new Color(0.8549019607843137, 0.8549019607843137, 0.9215686274509803, 1.0), // dadaeb
                                        new Color(0.38823529411764707, 0.38823529411764707, 0.38823529411764707, 1.0), // 636363
                                        new Color(0.5882352941176471, 0.5882352941176471, 0.5882352941176471, 1.0), // 969696
                                        new Color(0.7411764705882353, 0.7411764705882353, 0.7411764705882353, 1.0), // bdbdbd
                                        new Color(0.8509803921568627, 0.8509803921568627, 0.8509803921568627, 1.0) // d9d9d9
                        ));

        public static List<ColorMap> getColorMaps() {
                return Arrays.asList(Blues, BrBG, BuGn, BuPu, CMRmap, GnBu, Greens, Greys, OrRd, Oranges, PRGn, PiYG,
                                PuBu, PuBuGn, PuOr, PuRd, Purples, RdBu, RdGy, RdPu, RdYlBu, RdYlGn, Reds, Spectral,
                                wistia, YlGn, YlGnBu, YlOrBr, YlOrRd, afmhot, autumn, binary, bone, brg, bwr, cool,
                                coolwarm, copper, cubehelix, flag, gist_earth, gist_gray, gist_heat, gist_ncar,
                                gist_rainbow, gist_stern, gist_yarg, gnuplot, gnuplot2, gray, hot, hsv, jet,
                                nipy_spectral, ocean, pink, prism, rainbow, seismic, spring, summer, terrain, winter,
                                Accent, Dark2, Paired, Pastel1, Pastel2, Set1, Set2, Set3, tab10, tab20, tab20b,
                                tab20c);
        }
}