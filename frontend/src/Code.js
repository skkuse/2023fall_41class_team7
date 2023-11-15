import React from "react";
import "./index.css";
import "./Code.css";
import AceEditor from "react-ace";
import {useEffect, useState } from "react";
import "ace-builds/src-noconflict/mode-java";
import "ace-builds/src-noconflict/theme-monokai";
import "ace-builds/src-noconflict/ext-language_tools";

function onChange(newValue) {
  console.log("change", newValue);
}



function Code() {
  const [height, setHeight] = useState(500);
  let [fade, setFade] = useState("");

  const onClick=()=> {
    setHeight(1000);
  }

  const onClear=()=> {
    setHeight(500);
  }

  useEffect(() => {
    setTimeout(()=> {
      setFade("end");
    }, 300);
    return setFade("");
  }, [height]);

  return (
    <div className="w-full">
      <div className={'code_area start '+fade} onClick={onClick}>
        
        <AceEditor
          height={height}
          width='100%'
          placeholder="Placeholder Text"
          mode="java"
          theme="monokai"
          name="blah2"
          onChange={onChange}
          // onLoad={this.onLoad}
          // onChange={this.onChange}
          fontSize={16}
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
      <div style={{display: 'flex', justifyContent: "right", gap:10, padding:10}}>
        <button className="rounded bg-blue-500 px-4 py-2 font-bold text-white hover:bg-blue-600" >Upload</button>
        <button className="rounded bg-red-500 px-4 py-2 font-bold text-white hover:bg-red-600" onClick={onClear}>Clear</button>
        <button className="rounded bg-green-500 px-4 py-2 font-bold text-white hover:bg-green-600">Submit</button>
      </div>
      
      
    </div>
  );
}

export default Code;
