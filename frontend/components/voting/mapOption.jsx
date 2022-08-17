import React, { useState, useEffect } from "react";
import Option from "./option";

const MapOption = (props) => {
  const data = props.data.options;
  const [chosenOption, setChosenOption] = useState(0);
  console.log("mapoption data>>", data);

  if (!data) return <></>;

  return data.map((el, index) => (
    <Option
      data={el}
      index={index}
      chosenOption={chosenOption}
      setChosenOption={setChosenOption}
    />
  ));
};

export default MapOption;
