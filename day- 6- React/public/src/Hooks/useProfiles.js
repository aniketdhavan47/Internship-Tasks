import { useState ,useEffect} from "react";

const useGetProfiles=()=>{
    const [profiles, setProfiles] = useState([]);

    useEffect(() => {

          getProfiles();
          
    }, [])

    async function getProfiles(username) {

        const res = await fetch("https://api.github.com/users/akshaymarch7/followers");
        const data = await res.json();

        setProfiles(data);
    }

    return profiles;
}
export default useGetProfiles;