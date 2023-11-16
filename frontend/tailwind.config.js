/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
    "node_modules/flowbite-react/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        stat: {
          orange: '#F4C48B',
          skyblue: '#7BB8F1',
          grass: '#A7F98B',
        },
      },
    },
  },
  plugins: [require("flowbite/plugin")],
};
