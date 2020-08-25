import React, {Component} from "react";
import { Redirect} from "react-router-dom";

class NotFoundPage extends Component{
    state = {
        redirect: false,
    }

    componentDidMount() {
        setTimeout(() => {
            this.setState({
                redirect: true,
            })
        }, 2000)
    }

    render() {
        if (this.state.redirect) {
            return (
                <Redirect to={'/'} />
            )
        }

        return (
            <div>
                <h3 className="text-center">ERROR 404: Not Found</h3>
                <h5 className="text-center">Redirecting to homepage in two seconds!</h5>
            </div>
        )
    }
}

export default NotFoundPage;