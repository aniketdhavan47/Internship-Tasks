import { Component } from "react";
import { userNames } from "./constants";
import Profile from "./profile";
class About extends Component {
  constructor(props) {
    super(props);

    console.log("Parent constructor");
  }
  render() {
    return (
      <div className="about">
        <h1>GitHub Profiles</h1>
        <div className="profiles">
          {userNames.map((name) => (
            <Profile key={name} name={name} />
          ))}
        </div>
      </div>
    );
  }
}
export default About;
