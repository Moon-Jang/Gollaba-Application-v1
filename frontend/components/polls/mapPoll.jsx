import React, { useState, useEffect } from "react";
import Poll from "./poll";

const MapPoll = (props) => {
  const data = props.data;
  return data.map((el) => <Poll data={el} />);
};

export default MapPoll;
