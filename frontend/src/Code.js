import React from "react";
import "./index.css";
import AceEditor from "react-ace";

import "ace-builds/src-noconflict/mode-java";
import "ace-builds/src-noconflict/theme-monokai";
import "ace-builds/src-noconflict/ext-language_tools";

function onChange(newValue) {
  console.log("change", newValue);
}

function Code() {
  //   const code = "var message = 'Monaco Editor!' \nconsole.log(message);";
  return (
    <div class="m-10">
      <div class="justify-center items-center ">
        <div class="text-3xl font-extrabold mb-10">Let's Calculate!!</div>
        <AceEditor
          width="600px"
          placeholder="Placeholder Text"
          mode="java"
          theme="monokai"
          name="blah2"
          onChange={onChange}
          // onLoad={this.onLoad}
          // onChange={this.onChange}
          fontSize={14}
          showPrintMargin={true}
          showGutter={true}
          highlightActiveLine={true}
          value={`function onLoad(editor) {
    console.log("i've loaded");
  }`}
          setOptions={{
            enableBasicAutocompletion: false,
            enableLiveAutocompletion: false,
            enableSnippets: false,
            showLineNumbers: true,
            tabSize: 2,
          }}
        />
      </div>
    </div>
  );
}

export default Code;
