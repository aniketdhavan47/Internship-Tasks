import { useState } from "react"

const Section = ({ title, description ,isVisible,setIsVisible,setHide}) => {

    return (
        <div className="border border-black p-2 m-2">
            <h1 className="text-2xl font-bold"> {title}</h1>
            {(isVisible) ? (
                <>

                    <button className="p-2 text-xl underline"
                    onClick={()=>{
                        setHide();
                    }}
                    >Hide</button>
                        <p>{description}</p>
                
                    </>
            ) : (
                <button className="p-2 text-xl underline "
                onClick={()=>{
                    setIsVisible(true);
                }}
                >Show</button>)}




        </div>
    )
}


const Blogs = () => {
    const [isVisible,setIsVisible]=useState("about");
    return (
        <>

            <Section title={"About"} isVisible={isVisible==="about"} 
                    setIsVisible={()=>{
                        setIsVisible("about");
                    }}
                    setHide={()=>{
                        setIsVisible("");
                    }}
                    description={"Lorem ipsum dolor sit amet, consectetur adipisicing elit. Cum asperiores, odit culpa, voluptatem voluptatum fugiat nihil vel dolor dignissimos veritatis adipisci nisi nobis ullam? Consectetur, id neque! Unde nihil vero veritatis asperiores libero ut labore, itaque hic eos quidem molestiae iusto cupiditate, adipisci tenetur magnam sint ullam similique sit veniam omnis cum et dicta praesentium. Rem sint, sequi at ipsum pariatur optio consequuntur voluptate voluptas. Vero voluptatum explicabo magnam, ut distinctio incidunt architecto nam, quidem quisquam aperiam assumenda possimus! Quaerat iure quam voluptatem, amet, laboriosam facere unde asperiores laborum quis nobis itaque voluptates eum aut quo ipsam tempora assumenda accusamus?"} />
            <Section title={"Team"} 
                    isVisible={isVisible==="team"} 
                    setIsVisible={()=>{
                        setIsVisible("team");
                    }}
                    setHide={()=>{
                        setIsVisible("");
                    }}
                    description={"Lorem ipsum dolor sit amet, consectetur adipisicing elit. Cum asperiores, odit culpa, voluptatem voluptatum fugiat nihil vel dolor dignissimos veritatis adipisci nisi nobis ullam? Consectetur, id neque! Unde nihil vero veritatis asperiores libero ut labore, itaque hic eos quidem molestiae iusto cupiditate, adipisci tenetur magnam sint ullam similique sit veniam omnis cum et dicta praesentium. Rem sint, sequi at ipsum pariatur optio consequuntur voluptate voluptas. Vero voluptatum explicabo magnam, ut distinctio incidunt architecto nam, quidem quisquam aperiam assumenda possimus! Quaerat iure quam voluptatem, amet, laboriosam facere unde asperiores laborum quis nobis itaque voluptates eum aut quo ipsam tempora assumenda accusamus?"} />
            <Section title={"Career"} 
                    isVisible={isVisible==="career"} 
                    setIsVisible={()=>{
                        setIsVisible("career");
                    }}
                    setHide={()=>{
                        setIsVisible("");
                    }}
                    description={"Lorem ipsum dolor sit amet, consectetur adipisicing elit. Cum asperiores, odit culpa, voluptatem voluptatum fugiat nihil vel dolor dignissimos veritatis adipisci nisi nobis ullam? Consectetur, id neque! Unde nihil vero veritatis asperiores libero ut labore, itaque hic eos quidem molestiae iusto cupiditate, adipisci tenetur magnam sint ullam similique sit veniam omnis cum et dicta praesentium. Rem sint, sequi at ipsum pariatur optio consequuntur voluptate voluptas. Vero voluptatum explicabo magnam, ut distinctio incidunt architecto nam, quidem quisquam aperiam assumenda possimus! Quaerat iure quam voluptatem, amet, laboriosam facere unde asperiores laborum quis nobis itaque voluptates eum aut quo ipsam tempora assumenda accusamus?"} />
             <Section title={"Products"} 
                    isVisible={isVisible==="products"} 
                    setIsVisible={()=>{
                        setIsVisible("products");
                    }}
                    setHide={()=>{
                        setIsVisible("");
                    }}
                    description={"Lorem ipsum dolor sit amet, consectetur adipisicing elit. Cum asperiores, odit culpa, voluptatem voluptatum fugiat nihil vel dolor dignissimos veritatis adipisci nisi nobis ullam? Consectetur, id neque! Unde nihil vero veritatis asperiores libero ut labore, itaque hic eos quidem molestiae iusto cupiditate, adipisci tenetur magnam sint ullam similique sit veniam omnis cum et dicta praesentium. Rem sint, sequi at ipsum pariatur optio consequuntur voluptate voluptas. Vero voluptatum explicabo magnam, ut distinctio incidunt architecto nam, quidem quisquam aperiam assumenda possimus! Quaerat iure quam voluptatem, amet, laboriosam facere unde asperiores laborum quis nobis itaque voluptates eum aut quo ipsam tempora assumenda accusamus?"} />
        </>
    )
}

export default Blogs;