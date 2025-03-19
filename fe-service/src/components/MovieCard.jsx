function MovieCard({movie}) {
    function handleClick() {
        console.log(`You clicked on ${movie.title}`);
    }

    return (
        <div className="movie-card">
            <div className="movie-poster">
                <img src={movie.url} alt={movie.title}/>
                <div className="movie-overlay">
                    <button onClick={handleClick}>click here</button>
                </div>
            </div>
            <div className="movie-info">
                <h2>{movie.title}</h2>
                <p>{movie.description}</p>
            </div>
        </div>
    )
}

export default MovieCard;