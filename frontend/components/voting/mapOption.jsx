import React, { useState, useEffect } from "react";
import Option from "./option";

const MapOption = (props) => {
  const data = props.data.options;
  const voted = props.voted;
  const setVoted = props.setVoted;
  const responseType = props.data.responseType;

  if (!data) return <></>;
  return data.map((el, index) => (
    <Option
      data={el}
      responseType={responseType}
      index={index}
      voted={voted}
      setVoted={setVoted}
    />
  ));
};

export default MapOption;
