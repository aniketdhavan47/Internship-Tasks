import Card from "./card";
import Shimmer from "./Shimmer";
import useGetProfiles from "../Hooks/useProfiles";

const Body = () => {
    const profiles=useGetProfiles();
    const offline=true;
    if(offline){
        // return <h1>Check Internet Connection</h1>   
    }
   
    return (
        <>
            {(!profiles) ? <Shimmer /> : <div className="flex flex-wrap">

                {
                    profiles.map((profile) => {
                        return <Card {...profile} key={profile.id} />
                    })
                }

            </div>
            }
        </>
    )
}
export default Body;