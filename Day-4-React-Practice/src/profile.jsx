import { Component } from "react";
import "./styles.css";
import { Link } from "react-router-dom";

class Profile extends Component {
  constructor(props) {
    super(props);
    this.state = {
      userData: {},
    };
    console.log("Child constructor");
  }
  async componentDidMount() {
    const res = await fetch("https://api.github.com/users/" + this.props.name);
    const data = await res.json();
    this.setState({ userData: data });
    console.log(data);
    console.log("Child componentDidMount");
  }
  componentWillUnmount() {
    console.log("Child componentWillUnmount");
  }
  render() {
    console.log("Child render");
    const { userData } = this.state;
    return (
      <>
        <center>
          <Link style={{ textDecoration: "none", color: "black" }}>
            <div className="cards">
              <p>userName:{this.props.name}</p>
              <div className="card">
                <div className="profile">
                  <img
                    src={
                      "https://avatars.githubusercontent.com/u/" +
                      userData.id +
                      "?v=4"
                    }
                    alt="Profile Image"
                  />
                  <h2>id:{userData.id}</h2>
                  <h3>Name:{userData.name}</h3>
                  <h4>Public Repos :{userData.public_repos}</h4>
                  <h4>Followers :{userData.followers}</h4>
                </div>
              </div>
            </div>
          </Link>
        </center>
      </>
    );
  }
}
export default Profile;
