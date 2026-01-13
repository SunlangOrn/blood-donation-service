package co.istab.blooddonationservice.infrastructure.database.elasticsearch;

import co.istab.blooddonationservice.domain.blood.donation.constant.DonationStatus;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;


@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Document(indexName = "elastic-service-donation")
public class DonationDocument {

    @Id
    private String id;

    @Field(name = "donationId", type = FieldType.Integer)
    private Integer donationId;

    @Field(name ="name", type = FieldType.Text)
    private String name;

    @Field(name ="location", type = FieldType.Text)
    private String location;

    @Field(name ="phoneNumber", type = FieldType.Text)
    private String phoneNumber;

    @Field(name ="quantity", type = FieldType.Integer)
    private Integer quantity;

    @Field(name ="typeBlood",  type = FieldType.Keyword)
    private String typeBlood;

    @Field(name ="status", type = FieldType.Keyword)
    private DonationStatus status;

    @Field(name ="createdAt",  type = FieldType.Date ,format = DateFormat.date_time)
    private Date createdAt;
}
