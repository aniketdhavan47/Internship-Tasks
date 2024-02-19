import Card from "./card";
import Shimmer from "./Shimmer";
import useGetProfiles from "../Hooks/useProfiles";
import useOnline from "../Hooks/useOnline";

const Body = () => {
    const profiles=useGetProfiles();
    
    const online=useOnline();
    if(!online){
        return( 
            <>
                <h1>You Are Offline,Check Your Internet Connection</h1>
            </>
        )
    }
      
    return (
        <>
            {(!profiles) ? <Shimmer /> : <div className="flex flex-wrap justify-evenly">

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