const path = require('path');
const CopyPlugin = require("copy-webpack-plugin");
const Dotenv = require('dotenv-webpack');

// Get the name of the appropriate environment variable (`.env`) file for this build/run of the app
const dotenvFile = process.env.API_LOCATION ? `.env.${process.env.API_LOCATION}` : '.env';

module.exports = {
  plugins: [
    new CopyPlugin({
      patterns: [
        {
          from: "static_assets", to: "../",
          globOptions: {
            ignore: ["**/.DS_Store"],
          },
        },
      ],
    }),
    new Dotenv({ path: dotenvFile }),
  ],
  optimization: {
    usedExports: true
  },
  entry: {
    searchPlaylists: path.resolve(__dirname, 'src', 'pages', 'searchPlaylists.js'),
    viewChart: path.resolve(__dirname, 'src', 'pages', 'viewChart.js'),
    viewSetList: path.resolve(__dirname, 'src', 'pages', 'viewSetList.js'),
    createChart: path.resolve(__dirname, 'src', 'pages', 'createChart.js'),
    createSetList: path.resolve(__dirname, 'src', 'pages', 'createSetList.js'),
    updateChart: path.resolve(__dirname, 'src', 'pages', 'updateChart.js'),
    updateSetList: path.resolve(__dirname,'src', 'pages','updateSetList.js'),
    searchCharts: path.resolve(__dirname,'src', 'pages','searchCharts.js'),
    browseCharts: path.resolve(__dirname,'src', 'pages','browseCharts.js'),
  },
  output: {
    path: path.resolve(__dirname, 'build', 'assets'),
    filename: '[name].js',
    publicPath: '/assets/'
  },
  devServer: {
    static: {
      directory: path.join(__dirname, 'static_assets'),
    },
    port: 8000,
    client: {
      // overlay shows a full-screen overlay in the browser when there are js compiler errors or warnings
      overlay: true,
    },
  }
}
