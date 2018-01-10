package mobile.client.gentree.gentreemobile.screen.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import gentree.common.configuration.enums.Gender;
import gentree.common.configuration.enums.RelationType;
import gentree.server.dto.MemberDTO;
import gentree.server.dto.RelationDTO;
import mobile.client.gentree.gentreemobile.R;

import java.util.ArrayList;

/**
 * Created by vanilka on 22/12/2017.
 */
public class RelationListAdapter extends ArrayAdapter<RelationDTO> {

    public RelationListAdapter(Context context, ArrayList<RelationDTO> relationList) {
        super(context, 0, relationList);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RelationDTO relationDTO = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_relation, parent, false);
        }

        ImageView photoLeft = (ImageView) convertView.findViewById(R.id.photoLeft);
        if (relationDTO.getLeft() == null) {
            photoLeft.setImageResource(R.drawable.noname_female);
        } else {
            setSimPhoto(photoLeft, relationDTO.getLeft());
        }

        ImageView photoRight = (ImageView) convertView.findViewById(R.id.photoRight);
        if (relationDTO.getRight() == null) {
            photoRight.setImageResource(R.drawable.noname_male);
        } else {
            setSimPhoto(photoRight, relationDTO.getRight());
        }

        ImageView photoType = (ImageView) convertView.findViewById(R.id.photoType);
        System.out.println("Relation Type Is  :  " +relationDTO.getType());
        setTypePhoto(photoType, relationDTO.getType());

        return convertView;
    }


    private void setSimPhoto(ImageView imageView, MemberDTO member) {
        try {
            byte[] decodedString = Base64.decode(member.getPhotoDTO().getEncodedPicture(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(Bitmap.createBitmap(decodedByte));
        } catch (Exception e) {
            e.printStackTrace();
            imageView.setImageResource(member.getGender() == Gender.F ? R.drawable.generic_female : R.drawable.generic_male);
        }
    }

    private void setTypePhoto(ImageView imageView, RelationType relationType) {
        int intType;

        switch (relationType) {
            case LOVE: intType = R.drawable.type_love; break;
            case FIANCE: intType = R.drawable.type_fiance; break;
            case MARRIED: intType = R.drawable.type_maried; break;
            default:
                intType = R.drawable.type_neutral;
        }
        imageView.setImageResource(intType);
    }
}
