import { useEffect, useState } from "react";
import { useParams ,Link} from "react-router-dom";

import Shimmer from "./Shimmer";
import useGetProfile from "../Hooks/useProfile";

const ShowProfile=()=>{
    const {username}=useParams();
    const profile=useGetProfile(username);
    return (!profile)?<Shimmer/>:(
        
            <>
               <center>
               <div className="w-full pt-2 mt-10 ml-5 max-w-sm bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
            
                    <div className="flex flex-col items-center pb-10">
                        <img className="w-24  h-24 mb-3 rounded-full shadow-lg" src={profile.avatar_url} alt="Bonnie image"/>
                        <h5 className="mb-1 text-xl font-medium text-gray-900 dark:text-white">{profile.name}</h5>
                        <p className="mb-1  font-small  dark:text-white">{profile.bio}</p>
                        <span className="text-sm text-gray-500 dark:text-gray-400 font-bold"> Id: {profile.id}</span>
                        <span className="text-sm text-gray-500 dark:text-gray-400 font-bold"> Public Repos: {profile.public_repos}</span>
                        <span className="text-sm text-gray-500 dark:text-gray-400 font-bold"> Public Gists: {profile.public_gists}</span>
                        <span className="text-sm text-gray-500 dark:text-gray-400 font-bold"> Followers: {profile.followers}</span>
                        <span className="text-sm text-gray-500 dark:text-gray-400 font-bold" >Following: {profile.following}</span>
                        <div className="flex mt-4 md:mt-6"> 
                        <Link className="inline-flex items-center px-4 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Follow</Link>
                        <Link to={"/"} className="inline-flex items-center px-4 py-2 text-sm font-medium text-center text-gray-900 bg-white border border-gray-300 rounded-lg hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-700 dark:focus:ring-gray-700 ms-3">Home</Link>
                        </div>
                    </div>
                </div>
               </center>
            </>
        
    )
}
export default ShowProfile;