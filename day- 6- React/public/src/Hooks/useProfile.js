import { useEffect, useState } from "react";

const useGetProfile=(username)=>{
    const [profile,setProfile]=useState(null);
    useEffect(()=>{
        getProfiles();
    },[]);

    async function getProfiles(){
        const res=await fetch("https://api.github.com/users/"+username);
        const data=await res.json();
        setProfile(data);
    }
    return profile;
}
export default useGetProfile;