
import React, {Component} from 'react';
import PropTypes from 'prop-types';

class Checkbox extends Component {



    state = {
        isChecked: this.props.checked,
    }

    toggleCheckboxChange = () => {
        const { handleCheckboxChange, label } = this.props;

        this.setState(({ isChecked }) => (
            {
                isChecked: !isChecked,
            }
        ));

        handleCheckboxChange(label);
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.checked !== this.state.isChecked) {
            this.setState({ isChecked: nextProps.checked });
        }
    }

    render() {
        const { label } = this.props;

        // if(this.props.checked === true) {
        //     this.setState({'isChecked':true});
        // }

        const { isChecked } = this.state;


        console.log(isChecked)
        console.log( this.props.label + " " + this.props.checked)

        return (
            <div className="checkbox">
                <label>
                    <input
                        type="checkbox"
                        value={label}
                        checked={isChecked}
                        onChange={this.toggleCheckboxChange}
                    />

                </label>
            </div>
        );
    }
}

Checkbox.propTypes = {
    label: PropTypes.string.isRequired,
    handleCheckboxChange: PropTypes.func.isRequired,
};

export default Checkbox;

