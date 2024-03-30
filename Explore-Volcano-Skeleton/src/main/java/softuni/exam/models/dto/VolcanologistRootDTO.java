package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "volcanologists")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistRootDTO implements Serializable {
    @XmlElement(name = "volcanologist")
    private List<VolcanologisSeedDTO> volcanologisSeedDTOS;

    public List<VolcanologisSeedDTO> getVolcanologisSeedDTOS() {
        return volcanologisSeedDTOS;
    }

    public void setVolcanologisSeedDTOS(List<VolcanologisSeedDTO> volcanologisSeedDTOS) {
        this.volcanologisSeedDTOS = volcanologisSeedDTOS;
    }
}
