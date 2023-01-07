import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dlp.databinding.DialogBottomsheetGroupcodeBinding
import com.example.dlp.ui.dialogs.InsertGroupCodeDialogInterface
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialog(
    insertGroupCodeDialogInterface: InsertGroupCodeDialogInterface
) : BottomSheetDialogFragment() {
    private lateinit var binding: DialogBottomsheetGroupcodeBinding

    // 액티비티에서 인터페이스를 받아오기
    private var insertGroupCodeDialogInterface: InsertGroupCodeDialogInterface = insertGroupCodeDialogInterface

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogBottomsheetGroupcodeBinding.inflate(inflater, container, false)

        dialogInit()

        return binding.root
    }

    private fun dialogInit() {

        binding.apply {
            btnInsertgroupcode.setOnClickListener {
                insertGroupCodeDialogInterface.onCompleteButtonClicked(etInsertgroupcode.editText?.text.toString())
                dismiss()
            }
        }

    }

}